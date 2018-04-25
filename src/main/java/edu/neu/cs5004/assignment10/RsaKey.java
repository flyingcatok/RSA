package edu.neu.cs5004.assignment10;

import java.math.BigInteger;

/**
 * Represents a rsa key.
 */
public abstract class RsaKey {
  
  private BigInteger modulus;
  
  /**
   * Creates a rsa key given a modulus.
   *
   * @param modulus the given modulus.
   */
  protected RsaKey(BigInteger modulus) {
    
    this.modulus = modulus;
  }
  
  /**
   * Getter for the modulus.
   *
   * @return the modulus.
   */
  public BigInteger getModulus( ) {
    return modulus;
  }
  
}
