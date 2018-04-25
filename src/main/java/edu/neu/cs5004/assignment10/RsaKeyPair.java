package edu.neu.cs5004.assignment10;

/**
 * Represents a rsa key pair.
 */
public class RsaKeyPair {
  private RsaPublicKey publicKey;
  private RsaPrivateKey privateKey;
  
  /**
   * Creates a rsa key pair given a public key and a private key.
   *
   * @param publicKey  the given public key.
   * @param privateKey the given private key.
   */
  public RsaKeyPair(RsaPublicKey publicKey, RsaPrivateKey privateKey) {
    this.publicKey = publicKey;
    this.privateKey = privateKey;
  }
  
  /**
   * Getter for the public key.
   *
   * @return the public key.
   */
  public RsaPublicKey getPublicKey( ) {
    return publicKey;
  }
  
  /**
   * Getter for the private key.
   *
   * @return the private key.
   */
  public RsaPrivateKey getPrivateKey( ) {
    return privateKey;
  }
}
