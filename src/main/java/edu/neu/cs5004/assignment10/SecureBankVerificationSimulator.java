package edu.neu.cs5004.assignment10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Represents a secure bank verification simulator.
 */
public class SecureBankVerificationSimulator {
  private static final Integer NUMBER_OF_CLIENTS_UPPER_LIMIT = new Integer( 50000 );
  private static final Integer NUMBER_OF_CLIENT_LOWER_LIMIT = new Integer( 0 );
  private static final Integer NUMBER_OF_VERIFICATIONS_UPPER_LIMIT = new Integer( 10000 );
  private static final Integer NUMBER_OF_VERIFICATIONS_LOWER_LIMIT = new Integer( 0 );
  private static final Integer MESSAGE_UPPER_LIMIT = new Integer( 30000 );
  private static final Integer MESSAGE_LOWER_LIMIT = new Integer( 0 );
  private static final Integer DEPOSIT_UPPER_LIMIT = new Integer( 2000 );
  private static final Integer DEPOSIT_LOWER_LIMIT = new Integer( 0 );
  private static final Integer WITHDRAWAL_UPPER_LIMIT = new Integer( 3000 );
  private static final Integer WITHDRAWAL_LOWER_LIMIT = new Integer( 0 );
  private static final Double PERCENTAGE_OF_INVALID_MESSAGES_UPPER_LIMIT
      = new Double( 100.0 );
  private static final Double PERCENTAGE_OF_INVALID_MESSAGES_LOWER_LIMIT = new Double( 0.0 );
  
  private Integer numberOfClients;
  private Integer numberOfVerifications;
  private Double percentageOfInvalidMsg;
  private String outputFileName;
  
  private Map<Client, SignedMessage> clientsWithSignedMessages;
  
  private Bank bank;
  
  /**
   * Creates a secure bank verification simulator given the number of clients,
   * the number of verifications, the percentage of invalid messages and the output file name.
   *
   * @param numberOfClients        the number of clients.
   * @param numberOfVerifications  the number of verifications.
   * @param percentageOfInvalidMsg the percentage of invalid messages.
   * @param outputFileName         the output file name.
   */
  public SecureBankVerificationSimulator(Integer numberOfClients, Integer numberOfVerifications,
                                         Double percentageOfInvalidMsg, String outputFileName) {
    argumentIsBetween( "numberOfClients",
        numberOfClients, NUMBER_OF_CLIENT_LOWER_LIMIT, NUMBER_OF_CLIENTS_UPPER_LIMIT );
    argumentIsBetween( "numberOfVerifications",
        numberOfVerifications, NUMBER_OF_VERIFICATIONS_LOWER_LIMIT,
        NUMBER_OF_VERIFICATIONS_UPPER_LIMIT );
    argumentIsBetween( "percentageOfInvalidMsg",
        percentageOfInvalidMsg, PERCENTAGE_OF_INVALID_MESSAGES_LOWER_LIMIT,
        PERCENTAGE_OF_INVALID_MESSAGES_UPPER_LIMIT );
    
    this.numberOfClients = numberOfClients;
    this.numberOfVerifications = numberOfVerifications;
    this.percentageOfInvalidMsg = percentageOfInvalidMsg;
    this.outputFileName = outputFileName;
    this.clientsWithSignedMessages = new HashMap<>();
    this.bank = new Bank( "Secure Bank, N.A" );
  }
  
  /**
   *
   * @param argumentName
   * @param argument
   * @param lower
   * @param upper
   * @param <T>
   */
  private static <T extends Number> void argumentIsBetween(String argumentName,
                                                           T argument, T lower, T upper) {
    if ( argument.doubleValue() < lower.doubleValue()
        || argument.doubleValue() > upper.doubleValue() ) {
      throw new IllegalArgumentException( "Argument " + argumentName + " "
          + argument + " is invalid. Lower limit: " + lower + ", Upper limit: " + upper );
    }
  }
  
  private static Client generateClient( ) {
    return new Client( Randomness.getUniqueRandomInteger(), RsaAlgorithm.generateRsaKeyPair() );
  }
  
  // TODO: get better
  private List<Client> generateClients( ) {
    List<Client> clients = new ArrayList<>();
    IntStream.rangeClosed( 1, this.numberOfClients )
        .parallel().forEach( ele -> clients.add( generateClient() ) );
    
    return clients;
  }
  
  private void generateVerifications( ) {
    Integer numberOfInvalidVerifications
        = new Double( this.numberOfVerifications * this.percentageOfInvalidMsg / 100 )
        .intValue();
    Integer numberOfValidVerifications
        = this.numberOfVerifications - numberOfInvalidVerifications;
    List<Client> clientList = generateClients();
    Collections.shuffle( clientList );
    List<Client> clientsWithInvalidVerifications = clientList.subList( 0,
        numberOfInvalidVerifications );
    List<Client> clientsWithValidVerifications
        = clientList.subList( clientList.size() - numberOfValidVerifications, clientList.size() );
    
    // TODO: remove after check
    if ( clientsWithInvalidVerifications.size() != numberOfInvalidVerifications ) {
      throw new IndexOutOfBoundsException( "number of invalid verification client is wrong" );
    }
    
    // TODO: remove after check
    if ( clientsWithValidVerifications.size() != numberOfValidVerifications ) {
      throw new IndexOutOfBoundsException( "number of valid verification client is wrong" );
    }
    
    for (Client client : clientsWithInvalidVerifications) {
      this.clientsWithSignedMessages.put( client,
          generateInvalidSignedMessage( generateRandomMessage() ) );
    }
    
    for (Client client : clientsWithValidVerifications) {
      this.clientsWithSignedMessages.put( client,
          generateValidSignedMessage( generateRandomMessage(),
              client.getRsaKeyPair().getPrivateKey() ) );
    }
  }
  
  private Message generateRandomMessage( ) {
    Integer randomMessage
        = Randomness.getRandomInteger( MESSAGE_LOWER_LIMIT, MESSAGE_UPPER_LIMIT );
    return new Message( randomMessage );
  }
  
  private SignedMessage generateValidSignedMessage(Message message, RsaPrivateKey privateKey) {
    return new SignedMessage( message,
        RsaAlgorithm.generateDigitalSignature( message.getPlainNumber(), privateKey ) );
  }
  
  private SignedMessage generateInvalidSignedMessage(Message message) {
    return new SignedMessage( message, new DigitalSignature( Randomness.getRandomBigInteger() ) );
  }
  
  private Integer generateRandomDepositLimit( ) {
    return Randomness.getRandomInteger( DEPOSIT_LOWER_LIMIT, DEPOSIT_UPPER_LIMIT );
  }
  
  private Integer generateRandomWithdrawalLimit( ) {
    return Randomness.getRandomInteger( WITHDRAWAL_LOWER_LIMIT, WITHDRAWAL_UPPER_LIMIT );
  }
  
  private PublicClientInfo getPublicClientInfo(Client client) {
    PublicClientInfo clientInfo = new PublicClientInfo();
    clientInfo.setClientId( client.getClientId() );
    clientInfo.setPublicKey( client.getRsaKeyPair().getPublicKey() );
    clientInfo.setDepositLimit( generateRandomDepositLimit() );
    clientInfo.setWithdrawalLimit( generateRandomWithdrawalLimit() );
    
    return clientInfo;
  }
  
  private void generateBankClientPortfolio( ) {
    for (Client client : this.clientsWithSignedMessages.keySet()) {
      this.bank.getClientInfoMap().put( client.getClientId(), getPublicClientInfo( client ) );
    }
  }
  
  private void setUp( ) {
    this.generateVerifications();
    this.generateBankClientPortfolio();
  }
  
  public void run( ) {
    this.setUp();
  }
  
}
