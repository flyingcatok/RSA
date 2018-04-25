package edu.neu.cs5004.assignment10;

import java.math.BigInteger;

/**
 * Represents a digital signature.
 */
public class DigitalSignature {
  private BigInteger value;
  
  /**
   * Creates a digital signature given a big integer.
   *
   * @param value the given big integer
   */
  public DigitalSignature(BigInteger value) {
    
    this.value = value;
  }
  
  /**
   * Getter for the value.
   *
   * @return the value.
   */
  public BigInteger getValue( ) {
    return value;
  }
}
