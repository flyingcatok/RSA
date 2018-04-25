package edu.neu.cs5004.assignment10;

/**
 * Represents a signed message.
 */
public class SignedMessage {
  private Message message;
  private DigitalSignature digitalSignature;
  
  /**
   * Creates a signed message given a message and digital signature.
   *
   * @param message          the given message
   * @param digitalSignature the given digital signature
   */
  public SignedMessage(Message message, DigitalSignature digitalSignature) {
    this.message = message;
    this.digitalSignature = digitalSignature;
  }
  
  /**
   * Getter for the message.
   *
   * @return the message.
   */
  public Message getMessage( ) {
    return message;
  }
  
  /**
   * Getter for the digital signature.
   *
   * @return the digital signature
   */
  public DigitalSignature getDigitalSignature( ) {
    return digitalSignature;
  }
}
