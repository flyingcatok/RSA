package edu.neu.cs5004.assignment10;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Randomness {
    private static final ConcurrentMap<Integer, String> integerSet = new ConcurrentHashMap<>();

    private static final SecureRandom rand = new SecureRandom();

    private static final Integer bits = 1024;

    public static Integer getRandomInteger(Integer lowerLimit, Integer upperLimit) {
        return lowerLimit + rand.nextInt((upperLimit - lowerLimit) + 1);
    }

    public static Integer getUniqueRandomInteger() {
        Integer number;
        do {
            number = rand.nextInt();
        }
        while (integerSet.containsKey(number));
        integerSet.put(number, "");
        return number;
    }

    public static BigInteger getRandomBigInteger() {
        return new BigInteger(bits, rand);
    }

    public static BigInteger getRandomPrimeBigInteger() {
        return BigInteger.probablePrime(bits, rand);
    }
}
