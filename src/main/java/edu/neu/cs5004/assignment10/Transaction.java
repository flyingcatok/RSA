package edu.neu.cs5004.assignment10;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a transaction.
 */
public class Transaction {
  private LocalDate transactionNumber;
  private LocalTime time;
  private Integer clientId;
  private SignedMessage signedMessage;
  
  /**
   * Creates a transaction given a transaction number, time, a client id and a signed message.
   *
   * @param transactionNumber the transaction number.
   * @param time              local time.
   * @param clientId          the given client id.
   * @param signedMessage     the signed message.
   */
  public Transaction(LocalDate transactionNumber, LocalTime time, Integer clientId, SignedMessage signedMessage) {
    this.transactionNumber = transactionNumber;
    this.time = time;
    this.clientId = clientId;
    this.signedMessage = signedMessage;
  }
}
