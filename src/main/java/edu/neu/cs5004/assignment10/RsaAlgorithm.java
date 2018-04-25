package edu.neu.cs5004.assignment10;

import java.math.BigInteger;

public class RsaAlgorithm {

    public static RsaKeyPair generateRsaKeyPair() {
        BigInteger p = Randomness.getRandomPrimeBigInteger();
        BigInteger q = Randomness.getRandomPrimeBigInteger();
        BigInteger phi = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
        BigInteger n = p.multiply(q);
        BigInteger a = getPrivateKeyExponent(n, phi);
        BigInteger b = a.modInverse(phi);

        return new RsaKeyPair(new RsaPublicKey(n, b), new RsaPrivateKey(n, a));
    }

    public static DigitalSignature generateDigitalSignature(Integer integer, RsaPrivateKey privateKey) {
        return new DigitalSignature(BigInteger.valueOf(integer).modPow(privateKey.getExponent(), privateKey.getModulus()));
    }

    public static boolean verifyDigitalSignature(SignedMessage signedMessage, RsaPublicKey publicKey) {
        BigInteger mPrime = signedMessage.getDigitalSignature().getValue().modPow(publicKey.getExponent(), publicKey.getModulus());
        return mPrime.compareTo(BigInteger.valueOf(signedMessage.getMessage().getPlainNumber())) == 0;
    }

    private static BigInteger getPrivateKeyExponent(BigInteger n, BigInteger phi) {
        BigInteger startingExponent = Randomness.getRandomPrimeBigInteger();
        while (!startingExponent.gcd(n).equals(BigInteger.valueOf(1))
                || !startingExponent.gcd(phi).equals(BigInteger.valueOf(1))) {
            startingExponent = startingExponent.nextProbablePrime();
        }

        return startingExponent;
    }
}
