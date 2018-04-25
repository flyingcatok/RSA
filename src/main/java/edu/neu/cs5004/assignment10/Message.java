package edu.neu.cs5004.assignment10;

/**
 * Represents a massage.
 */
public class Message {
  private Integer amount;
  private Integer plainNumber;
  private BankingAction action;
  
  /**
   * Creates a message given a plain number.
   *
   * @param plainNumber the given plain number.
   */
  public Message(Integer plainNumber) {
    this.plainNumber = plainNumber;
    this.action = calculateAction( plainNumber );
    this.amount = plainNumber / 10;
  }
  
  /**
   * Generates a banking action according to the given number.
   *
   * @param generated the given number.
   * @return a banking action.
   */
  private static BankingAction calculateAction(Integer generated) {
    Integer actionDigit = generated % 10;
    
    return actionDigit >= 0 && actionDigit <= 4
        ? BankingAction.Deposit : BankingAction.Withdrawal;
  }
  
  /**
   * Getter for the amount.
   *
   * @return the amount.
   */
  public Integer getAmount( ) {
    return amount;
  }
  
  /**
   * Getter for the plain number.
   *
   * @return the plain number
   */
  public Integer getPlainNumber( ) {
    return plainNumber;
  }
  
  /**
   * Getter for the banking action.
   *
   * @return the banking action.
   */
  public BankingAction getAction( ) {
    return action;
  }
}
