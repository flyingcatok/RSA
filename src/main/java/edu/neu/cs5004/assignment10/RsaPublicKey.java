package edu.neu.cs5004.assignment10;

import java.math.BigInteger;

/**
 * Represents a rsa public key.
 */
public class RsaPublicKey extends RsaKey {
  
  private BigInteger exponent;
  
  /**
   * Creates a rsa public key given a modulus and an exponent.
   *
   * @param modulus  the given modulus.
   * @param exponent the given exponent.
   */
  public RsaPublicKey(BigInteger modulus, BigInteger exponent) {
    super( modulus );
    this.exponent = exponent;
  }
  
  /**
   * Getter for the exponent.
   *
   * @return the exponent.
   */
  public BigInteger getExponent( ) {
    return exponent;
  }
}
