package edu.neu.cs5004.assignment10;

public class PublicClientInfo {
    private Integer id;
    private RsaPublicKey publishKey;
    private Integer withdrawalLimit;
    private Integer depositLimit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RsaPublicKey getPublishKey() {
        return publishKey;
    }

    public void setPublishKey(RsaPublicKey publishKey) {
        this.publishKey = publishKey;
    }

    public Integer getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(Integer withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }

    public Integer getDepositLimit() {
        return depositLimit;
    }

    public void setDepositLimit(Integer depositLimit) {
        this.depositLimit = depositLimit;
    }
}
