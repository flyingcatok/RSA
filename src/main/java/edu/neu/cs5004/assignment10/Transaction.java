package edu.neu.cs5004.assignment10;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate transactionNumber;
    private LocalTime time;
    private Integer clientId;
    private SignedMessage signedMessage;

    public Transaction(LocalDate transactionNumber, LocalTime time, Integer clientId, SignedMessage signedMessage) {
        this.transactionNumber = transactionNumber;
        this.time = time;
        this.clientId = clientId;
        this.signedMessage = signedMessage;
    }
}
