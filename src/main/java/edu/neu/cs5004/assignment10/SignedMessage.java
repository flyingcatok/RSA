package edu.neu.cs5004.assignment10;

public class SignedMessage {
    private Message message;
    private DigitalSignature digitalSignature;

    public SignedMessage(Message message, DigitalSignature digitalSignature) {
        this.message = message;
        this.digitalSignature = digitalSignature;
    }

    public Message getMessage() {
        return message;
    }

    public DigitalSignature getDigitalSignature() {
        return digitalSignature;
    }
}
