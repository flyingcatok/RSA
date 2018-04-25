package edu.neu.cs5004.assignment10;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Represents a randomness.
 */
public class Randomness {
  private static final ConcurrentMap<Integer, String> INTEGER_SET = new ConcurrentHashMap<>();
  
  private static final SecureRandom RAND = new SecureRandom();
  
  private static final Integer BITS = 1024;
  
  /**
   * Generates a random integer.
   *
   * @param lowerLimit the lower bound.
   * @param upperLimit the upper bound.
   * @return a random integer.
   */
  public static Integer getRandomInteger(Integer lowerLimit, Integer upperLimit) {
    return lowerLimit + RAND.nextInt( (upperLimit - lowerLimit) + 1 );
  }
  
  /**
   * Generates an unique random integer.
   *
   * @return an unique random integer.
   */
  public static Integer getUniqueRandomInteger( ) {
    Integer number;
    do {
      number = RAND.nextInt();
    }
    while (INTEGER_SET.containsKey( number ));
    INTEGER_SET.put( number, "" );
    return number;
  }
  
  /**
   * Generates a random big integer.
   *
   * @return a random big integer.
   */
  public static BigInteger getRandomBigInteger( ) {
    return new BigInteger( BITS, RAND );
  }
  
  /**
   * Generates a random prime big integer.
   *
   * @return a random prime big integer.
   */
  public static BigInteger getRandomPrimeBigInteger( ) {
    return BigInteger.probablePrime( BITS, RAND );
  }
}
