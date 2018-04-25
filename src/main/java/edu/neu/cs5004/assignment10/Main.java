package edu.neu.cs5004.assignment10;

/**
 * SecureBankVerificationSimulator entry
 */
public class Main {
    public static void main(String[] args) {
        SecureBankVerificationSimulator sim = new SecureBankVerificationSimulator(50, 20, 50.0, "output.csv");
        sim.run();

    }
}
