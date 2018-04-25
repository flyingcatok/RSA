package edu.neu.cs5004.assignment10;

public class Message {
    private Integer amount;
    private Integer plainNumber;
    private BankingAction action;

    public Message(Integer plainNumber) {
        this.plainNumber = plainNumber;
        this.action = calculateAction(plainNumber);
        this.amount = plainNumber / 10;
    }

    private static BankingAction calculateAction(Integer generated) {
        Integer actionDigit = generated % 10;

        return actionDigit >= 0 && actionDigit <= 4 ? BankingAction.Deposit : BankingAction.Withdrawal;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getPlainNumber() {
        return plainNumber;
    }

    public BankingAction getAction() {
        return action;
    }
}
