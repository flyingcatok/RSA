package edu.neu.cs5004.assignment10;

import java.math.BigInteger;

public class RsaPublicKey extends RsaKey {

    private BigInteger exponent;

    public RsaPublicKey(BigInteger modulus, BigInteger exponent) {
        super(modulus);
        this.exponent = exponent;
    }

    public BigInteger getExponent() {
        return exponent;
    }
}
