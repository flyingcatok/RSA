package edu.neu.cs5004.assignment10;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate transactionNumber;
    private LocalTime time;
    private Integer clientId;
    private SignedMessage signedMessage;
    private Bank.ProcessingResult processingResult;

    public Transaction(LocalDate transactionNumber, LocalTime time, Integer clientId, SignedMessage signedMessage, Bank.ProcessingResult processingResult) {
        this.transactionNumber = transactionNumber;
        this.time = time;
        this.clientId = clientId;
        this.signedMessage = signedMessage;
        this.processingResult = processingResult;
    }

    public LocalDate getTransactionNumber() {
        return transactionNumber;
    }

    public LocalTime getTime() {
        return time;
    }

    public Integer getClientId() {
        return clientId;
    }

    public SignedMessage getSignedMessage() {
        return signedMessage;
    }

    public boolean isVerified() {
        return this.processingResult.isMessageVerified();
    }

    public TransactionStatus getTransactionStatus() {
        if(this.signedMessage.getMessage().getAction() == BankingAction.Withdrawal) {
            return this.processingResult.isBankingActionAccepted() ? TransactionStatus.WithdrawalAccepted : TransactionStatus.WithdrawalRejected;
        }

        if(this.signedMessage.getMessage().getAction() == BankingAction.Deposit) {
            return this.processingResult.isBankingActionAccepted() ? TransactionStatus.DepositAccepted : TransactionStatus.DepositRejected;
        }

        throw new IllegalStateException("Banking Action is in illegal state.");
    }
}
