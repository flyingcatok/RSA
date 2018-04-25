package edu.neu.cs5004.assignment10;

import java.util.Objects;

/**
 * Represents a client.
 */
public class Client {
  private Integer clientId;
  private RsaKeyPair rsaKeyPair;
  
  
  /**
   * Creates a client given an id.
   *
   * @param clientId the given id.
   */
  public Client(Integer clientId) {
    this.clientId = clientId;
  }
  
  /**
   * Creates a client given an id and a rsa key pair.
   *
   * @param clientId         the given id.
   * @param rsaKeyPair the given rsa key pair.
   */
  public Client(Integer clientId, RsaKeyPair rsaKeyPair) {
    this.clientId = clientId;
    this.rsaKeyPair = rsaKeyPair;
  }
  
  @Override
  public boolean equals(Object obj) {
    if ( this == obj ) {
      return true;
    }
    if ( obj == null || getClass() != obj.getClass() ) {
      return false;
    }
    Client client = (Client) obj;
    return Objects.equals( clientId, client.clientId );
  }
  
  @Override
  public int hashCode( ) {
    
    return Objects.hash( clientId );
  }
  
  /**
   * Getter for the client id.
   *
   * @return the client id
   */
  public Integer getClientId( ) {
    return clientId;
  }
  
  /**
   * Getter for the rsa key pair.
   *
   * @return the rsa key pair
   */
  public RsaKeyPair getRsaKeyPair( ) {
    return rsaKeyPair;
  }
  
  /**
   * Setter for the rsa key pair.
   *
   * @param rsaKeyPair the rsa key pair.
   */
  public void setRsaKeyPair(RsaKeyPair rsaKeyPair) {
    this.rsaKeyPair = rsaKeyPair;
  }
}


