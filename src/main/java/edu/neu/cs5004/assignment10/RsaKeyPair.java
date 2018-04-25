package edu.neu.cs5004.assignment10;

public class RsaKeyPair {
    private RsaPublicKey PublicKey;
    private RsaPrivateKey PrivateKey;

    public RsaKeyPair(RsaPublicKey publicKey, RsaPrivateKey privateKey) {
        PublicKey = publicKey;
        PrivateKey = privateKey;
    }

    public RsaPublicKey getPublicKey() {
        return PublicKey;
    }

    public RsaPrivateKey getPrivateKey() {
        return PrivateKey;
    }
}
