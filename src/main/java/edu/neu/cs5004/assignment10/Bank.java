package edu.neu.cs5004.assignment10;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a bank.
 */
public class Bank {
  private String name;
  private Map<Integer, PublicClientInfo> clientInfoMap;
  
  /**
   * Creates a bank given a name as a string.
   *
   * @param name the bank name.
   */
  public Bank(String name) {
    this.name = name;
    this.clientInfoMap = new HashMap<>();
  }
  
  /**
   * Getter for the bank name.
   *
   * @return the bank name
   */
  public String getName( ) {
    return name;
  }
  
  /**
   * Getter for the client information.
   *
   * @return the client information
   */
  public Map<Integer, PublicClientInfo> getClientInfoMap( ) {
    return clientInfoMap;
  }
  
  /**
   * Process the signed message with a client id.
   *
   * @param clientId      the given client id.
   * @param signedMessage the message.
   * @return true if the message is valid, false otherwise.
   */
  public boolean processSignedMessage(Integer clientId, SignedMessage signedMessage) {
    if ( !this.clientInfoMap.containsKey( clientId ) ) {
      throw new IllegalArgumentException( "Invalid client id: " + clientId );
    }
    
    if ( signedMessage == null ) {
      throw new IllegalArgumentException( "NULL signed message." );
    }
    
    PublicClientInfo clientInfo = this.clientInfoMap.get( clientId );
    boolean isSignatureValid = RsaAlgorithm.verifyDigitalSignature( signedMessage,
        clientInfo.getPublicKey() );
    
    if ( !isSignatureValid ) {
      return false;
    }
    
    Message message = signedMessage.getMessage();
    
    if ( message.getAction() == BankingAction.Deposit ) {
      return message.getAmount() <= clientInfo.getDepositLimit();
    }
    
    if ( message.getAction() == BankingAction.Withdrawal ) {
      return message.getAmount() <= clientInfo.getWithdrawalLimit();
    }
    
    return false;
  }
}
