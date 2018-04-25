package edu.neu.cs5004.assignment10;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private String name;
    private Map<Integer, PublicClientInfo> clientInfoMap;

    public Bank(String name) {
        this.name = name;
        this.clientInfoMap = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<Integer, PublicClientInfo> getClientInfoMap() {
        return clientInfoMap;
    }

    public boolean processSignedMessage(Integer clientId, SignedMessage signedMessage) {
        if (!this.clientInfoMap.containsKey(clientId)) {
            throw new IllegalArgumentException("Invalid client id: " + clientId);
        }

        if (signedMessage == null) {
            throw new IllegalArgumentException("NULL signed message.");
        }

        PublicClientInfo clientInfo = this.clientInfoMap.get(clientId);
        boolean isSignatureValid = RsaAlgorithm.verifyDigitalSignature(signedMessage, clientInfo.getPublishKey());

        if (!isSignatureValid) {
            return false;
        }

        Message message = signedMessage.getMessage();

        if (message.getAction() == BankingAction.Deposit) {
            return message.getAmount() <= clientInfo.getDepositLimit();
        }

        if (message.getAction() == BankingAction.Withdrawal) {
            return message.getAmount() <= clientInfo.getWithdrawalLimit();
        }

        return false;
    }
}
