package edu.neu.cs5004;

import edu.neu.cs5004.assignment10.SecureBankVerificationSimulator;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        SecureBankVerificationSimulator sim = new SecureBankVerificationSimulator(500, 200, 50.0, "output.csv");
        sim.Run();

    }
}
