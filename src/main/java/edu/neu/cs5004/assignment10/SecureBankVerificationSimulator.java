package edu.neu.cs5004.assignment10;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class SecureBankVerificationSimulator {
    private static final Integer NUMBER_OF_CLIENTS_UPPER_LIMIT = new Integer(50000);
    private static final Integer NUMBER_OF_CLIENT_LOWER_LIMIT = new Integer(0);
    private static final Integer NUMBER_OF_VERIFICATIONS_UPPER_LIMIT = new Integer(10000);
    private static final Integer NUMBER_OF_VERIFICATIONS_LOWER_LIMIT = new Integer(0);
    private static final Integer MESSAGE_UPPER_LIMIT = new Integer(30000);
    private static final Integer MESSAGE_LOWER_LIMIT = new Integer(0);
    private static final Integer DEPOSIT_UPPER_LIMIT = new Integer(2000);
    private static final Integer DEPOSIT_LOWER_LIMIT = new Integer(0);
    private static final Integer WITHDRAWAL_UPPER_LIMIT = new Integer(3000);
    private static final Integer WITHDRAWAL_LOWER_LIMIT = new Integer(0);
    private static final Double PERCENTAGE_OF_INVALID_MESSAGES_UPPER_LIMIT = new Double(100.0);
    private static final Double PERCENTAGE_OF_INVALID_MESSAGES_LOWER_LIMIT = new Double(0.0);
    private static final String UTF = "UTF-8";
    private static final String FIRST_LINE = "Transaction number – Date,Time,Client ID,Message,Digital signature,Verified,Transactions status";
    private static final String REGEX_COMMA = ",";
    private static final String ERROR_MSG = "Something went wrong when write to file! ：";
    private Integer numberOfClients;
    private Integer numberOfVerifications;
    private Double percentageOfInvalidMsg;
    private String outputFileName;

    private Map<Client, SignedMessage> clientsWithSignedMessages;

    private Bank bank;

    public SecureBankVerificationSimulator(Integer numberOfClients, Integer numberOfVerifications, Double percentageOfInvalidMsg, String outputFileName) {
        argumentIsBetween("numberOfClients", numberOfClients, NUMBER_OF_CLIENT_LOWER_LIMIT, NUMBER_OF_CLIENTS_UPPER_LIMIT);
        argumentIsBetween("numberOfVerifications", numberOfVerifications, NUMBER_OF_VERIFICATIONS_LOWER_LIMIT, NUMBER_OF_VERIFICATIONS_UPPER_LIMIT);
        argumentIsBetween("percentageOfInvalidMsg", percentageOfInvalidMsg, PERCENTAGE_OF_INVALID_MESSAGES_LOWER_LIMIT, PERCENTAGE_OF_INVALID_MESSAGES_UPPER_LIMIT);
        checkFileName(outputFileName);

        this.numberOfClients = numberOfClients;
        this.numberOfVerifications = numberOfVerifications;
        this.percentageOfInvalidMsg = percentageOfInvalidMsg;
        this.outputFileName = outputFileName;
        this.clientsWithSignedMessages = new HashMap<>();
        this.bank = new Bank("Secure Bank, N.A");
    }

    private static <T extends Number> void argumentIsBetween(String argumentName, T argument, T lower, T upper) {
        if (argument.doubleValue() < lower.doubleValue() || argument.doubleValue() > upper.doubleValue()) {
            throw new IllegalArgumentException("Argument " + argumentName + " " + argument + " is invalid. Lower limit: " + lower + ", Upper limit: " + upper);
        }
    }

    private static void checkFileName(String outputFileName) {
        if (!outputFileName.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("Argument outputFileName is not ending with .csv.");
        }
    }

    private static Client generateClient() {
        return new Client(Randomness.getUniqueRandomInteger(), RsaAlgorithm.generateRsaKeyPair());
    }

    private static LocalDate getRandomLocalDate() {
        LocalDate endDate = LocalDate.now(); //end date
        long end = endDate.toEpochDay();
        LocalDate startDate = LocalDate.now().minusYears(5);
        long start = startDate.toEpochDay();

        long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
        return LocalDate.ofEpochDay(randomEpochDay);
    }

    private static LocalTime getRandomLocalTime() {
        return LocalTime.of(Randomness.getRandomInteger(0, 23), Randomness.getRandomInteger(0, 59), Randomness.getRandomInteger(0, 59));
    }

    private List<Client> generateClients() {
        List<Client> clients = new ArrayList<>();
        IntStream.rangeClosed(1, this.numberOfClients).parallel().forEach(i -> clients.add(generateClient()));

        return clients;
    }

    private void generateVerifications() {
        Integer numberOfInvalidVerifications = new Double(this.numberOfVerifications * this.percentageOfInvalidMsg / 100).intValue();
        Integer numberOfValidVerifications = this.numberOfVerifications - numberOfInvalidVerifications;
        List<Client> clientList = generateClients();
        Collections.shuffle(clientList);
        List<Client> clientsWithInvalidVerifications = clientList.subList(0, numberOfInvalidVerifications);
        List<Client> clientsWithValidVerifications = clientList.subList(clientList.size() - numberOfValidVerifications, clientList.size());

        for (Client client : clientsWithInvalidVerifications) {
            this.clientsWithSignedMessages.put(client, generateInvalidSignedMessage(generateRandomMessage()));
        }

        for (Client client : clientsWithValidVerifications) {
            this.clientsWithSignedMessages.put(client, generateValidSignedMessage(generateRandomMessage(), client.getRsaKeyPair().getPrivateKey()));
        }
    }

    private Message generateRandomMessage() {
        Integer randomMessage = Randomness.getRandomInteger(MESSAGE_LOWER_LIMIT, MESSAGE_UPPER_LIMIT);
        return new Message(randomMessage);
    }

    private SignedMessage generateValidSignedMessage(Message message, RsaPrivateKey privateKey) {
        return new SignedMessage(message, RsaAlgorithm.generateDigitalSignature(message.getPlainNumber(), privateKey));
    }

    private SignedMessage generateInvalidSignedMessage(Message message) {
        return new SignedMessage(message, new DigitalSignature(Randomness.getRandomBigInteger()));
    }

    private Integer generateRandomDepositLimit() {
        return Randomness.getRandomInteger(DEPOSIT_LOWER_LIMIT, DEPOSIT_UPPER_LIMIT);
    }

    private Integer generateRandomWithdrawalLimit() {
        return Randomness.getRandomInteger(WITHDRAWAL_LOWER_LIMIT, WITHDRAWAL_UPPER_LIMIT);
    }

    private PublicClientInfo getPublicClientInfo(Client client) {
        PublicClientInfo clientInfo = new PublicClientInfo();
        clientInfo.setId(client.getId());
        clientInfo.setPublishKey(client.getRsaKeyPair().getPublicKey());
        clientInfo.setDepositLimit(generateRandomDepositLimit());
        clientInfo.setWithdrawalLimit(generateRandomWithdrawalLimit());

        return clientInfo;
    }

    private void generateBankClientPortfolio() {
        for (Client client : this.clientsWithSignedMessages.keySet()) {
            this.bank.getClientInfoMap().put(client.getId(), getPublicClientInfo(client));
        }
    }

    private void setUp() {
        this.generateVerifications();
        this.generateBankClientPortfolio();
    }

    private void simulateTransactionProcessing() {
        List<Transaction> transactions = new ArrayList<>();

        for (Client client : this.clientsWithSignedMessages.keySet()) {
            SignedMessage signedMessage = this.clientsWithSignedMessages.get(client);
            Bank.ProcessingResult processingResult = this.bank.processSignedMessage(client.getId(), signedMessage);
            transactions.add(new Transaction(getRandomLocalDate(), getRandomLocalTime(), client.getId(), signedMessage, processingResult));
        }

        this.writeToFile(transactions);
    }

    public void Run() {
        this.setUp();
        this.simulateTransactionProcessing();
    }

    private void writeToFile(List<Transaction> transactions) {
        try (BufferedWriter outPutFile = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(this.outputFileName), UTF))) {
            outPutFile.write(FIRST_LINE);
            outPutFile.newLine();

            for (Transaction transaction : transactions) {
                outPutFile.write(transaction.getTransactionNumber().toString());
                outPutFile.write(REGEX_COMMA);
                outPutFile.write(transaction.getTime().toString());
                outPutFile.write(REGEX_COMMA);
                outPutFile.write(transaction.getClientId().toString());
                outPutFile.write(REGEX_COMMA);
                outPutFile.write(transaction.getSignedMessage().getMessage().getPlainNumber().toString());
                outPutFile.write(REGEX_COMMA);
                outPutFile.write(transaction.getSignedMessage().getDigitalSignature().getValue().toString());
                outPutFile.write(REGEX_COMMA);
                outPutFile.write(transaction.isVerified() ? "YES" : "NO");
                outPutFile.write(REGEX_COMMA);
                outPutFile.write(transaction.getTransactionStatus().toString());
                outPutFile.newLine();
            }
        } catch (IOException ioe) {
            System.out.println(ERROR_MSG + ioe.getMessage());
            ioe.printStackTrace();
        }
    }

}
