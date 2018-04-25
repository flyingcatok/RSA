package edu.neu.cs5004.assignment10;

/**
 * Represents a public client information.
 */
public class PublicClientInfo {
    private Integer clientId;
    private RsaPublicKey publicKey;
    private Integer withdrawalLimit;
    private Integer depositLimit;

    /**
     * Getter for the client id.
     *
     * @return the client id
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Setter for the client id.
     *
     * @param clientId the given client id.
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * Getter for the public key.
     *
     * @return the public key.
     */
    public RsaPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Setter for the public key.
     *
     * @param publicKey the given public key.
     */
    void setPublicKey(RsaPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * Getter for the withdraw limit.
     *
     * @return the withdraw limit.
     */
    public Integer getWithdrawalLimit() {
        return withdrawalLimit;
    }

    /**
     * Setter for the withdraw limit.
     *
     * @param withdrawalLimit the given withdraw limit.
     */
    void setWithdrawalLimit(Integer withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }

    /**
     * Getter for the deposit limit.
     *
     * @return the deposit limit.
     */
    public Integer getDepositLimit() {
        return depositLimit;
    }

    /**
     * Setter for the deposit limit.
     *
     * @param depositLimit the deposit limit.
     */
    void setDepositLimit(Integer depositLimit) {
        this.depositLimit = depositLimit;
    }
}
