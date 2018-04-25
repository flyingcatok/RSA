package edu.neu.cs5004.assignment10;

import java.math.BigInteger;

public class RsaPrivateKey extends RsaKey {

    private BigInteger exponent;

    public RsaPrivateKey(BigInteger modulus, BigInteger exponent) {
        super(modulus);
        this.exponent = exponent;
    }

    public BigInteger getExponent() {
        return exponent;
    }
}
