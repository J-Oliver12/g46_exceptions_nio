package org.example;

public class InsufficientFundsException extends RuntimeException {

    private  double balance;
    private double amount;

    public InsufficientFundsException(double balance, double amount, String message) {
        super(message);
        this.balance = balance;
        this.amount = amount;
    }


    public double getBalance() {
        return balance;
    }

    public double getAmount() {
        return amount;
    }








}
