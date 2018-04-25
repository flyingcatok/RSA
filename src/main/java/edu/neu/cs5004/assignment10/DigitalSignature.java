package edu.neu.cs5004.assignment10;

import java.math.BigInteger;

public class DigitalSignature {
    private BigInteger value;

    public DigitalSignature(BigInteger value) {

        this.value = value;
    }

    public BigInteger getValue() {
        return value;
    }
}
