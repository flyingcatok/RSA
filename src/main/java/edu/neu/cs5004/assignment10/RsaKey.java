package edu.neu.cs5004.assignment10;

import java.math.BigInteger;

public abstract class RsaKey {

    private BigInteger modulus;

    public BigInteger getModulus() {
        return modulus;
    }

    protected RsaKey(BigInteger modulus) {

        this.modulus = modulus;
    }
}
