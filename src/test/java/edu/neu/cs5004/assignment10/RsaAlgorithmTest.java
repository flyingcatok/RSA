package edu.neu.cs5004.assignment10;

import org.junit.Assert;
import org.junit.Test;

public class RsaAlgorithmTest {

    private RsaKeyPair keyPair = RsaAlgorithm.generateRsaKeyPair();
    private Integer number = 15013;

    @Test
    public void verifyDigitalSignature_true() {
        SignedMessage smsg = new SignedMessage(new Message(number), RsaAlgorithm.generateDigitalSignature(number, keyPair.getPrivateKey()));

        Assert.assertTrue(RsaAlgorithm.verifyDigitalSignature(smsg, keyPair.getPublicKey()));
    }

    @Test
    public void verifyDigitalSignature_false() {
        SignedMessage smsg = new SignedMessage(new Message(number), new DigitalSignature(Randomness.getRandomPrimeBigInteger()));

        Assert.assertFalse(RsaAlgorithm.verifyDigitalSignature(smsg, keyPair.getPublicKey()));
    }
}