package edu.neu.cs5004.assignment10;

import java.math.BigInteger;

/**
 * Represents a rsa algorithm.
 */
public class RsaAlgorithm {
  
  /**
   * Generates a rsa key pair.
   *
   * @return a rsa key pair.
   */
  public static RsaKeyPair generateRsaKeyPair( ) {
    BigInteger primeA = Randomness.getRandomPrimeBigInteger();
    BigInteger primeB = Randomness.getRandomPrimeBigInteger();
    BigInteger phi = primeA.subtract( BigInteger.valueOf( 1 ) )
        .multiply( primeB.subtract( BigInteger.valueOf( 1 ) ) );
    BigInteger modulus = primeA.multiply( primeB );
    BigInteger privateKeyExponent = getPrivateKeyExponent( modulus, phi );
    BigInteger publicKeyExponent = privateKeyExponent.modInverse( phi );
    
    return new RsaKeyPair( new RsaPublicKey( modulus, publicKeyExponent ),
        new RsaPrivateKey( modulus, privateKeyExponent ) );
  }
  
  /**
   * Generates a digital signature.
   *
   * @param integer    the given integer.
   * @param privateKey the given private key.
   * @return a digital signature.
   */
  public static DigitalSignature generateDigitalSignature(Integer integer,
                                                          RsaPrivateKey privateKey) {
    return new DigitalSignature( BigInteger.valueOf( integer ).modPow( privateKey.getExponent(),
        privateKey.getModulus() ) );
  }
  
  /**
   * Verify a digital signature.
   *
   * @param signedMessage the given signed message.
   * @param publicKey     the given public key.
   * @return true if the digital signature is valid, false otherwise.
   */
  public static boolean verifyDigitalSignature(SignedMessage signedMessage,
                                               RsaPublicKey publicKey) {
    BigInteger mPrime = signedMessage.getDigitalSignature().getValue()
        .modPow( publicKey.getExponent(), publicKey.getModulus() );
    return mPrime.compareTo( BigInteger.valueOf( signedMessage.getMessage()
        .getPlainNumber() ) ) == 0;
  }
  
  /**
   * Getter for the private key exponent.
   *
   * @param modulus the given modulus.
   * @param phi     the given phi.
   * @return the private key exponent.
   */
  private static BigInteger getPrivateKeyExponent(BigInteger modulus, BigInteger phi) {
    BigInteger startingExponent = Randomness.getRandomPrimeBigInteger();
    while (!startingExponent.gcd( modulus ).equals( BigInteger.valueOf( 1 ) )
        || !startingExponent.gcd( phi ).equals( BigInteger.valueOf( 1 ) )) {
      startingExponent = startingExponent.nextProbablePrime();
    }
    
    return startingExponent;
  }
}
