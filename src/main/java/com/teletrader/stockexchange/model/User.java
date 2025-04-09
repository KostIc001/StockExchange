package com.teletrader.stockexchange.model;

import java.util.Map;

public class User {

    private String id;
    private double balance;
    private Map<String, Integer> stocks; //Akcije koje korisnik poseduje

    public User(String id, double balance, Map<String, Integer> stocks) {
        this.id = id;
        this.balance = balance;
        this.stocks = stocks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Map<String, Integer> getStocks() {
        return stocks;
    }

    public void setStocks(Map<String, Integer> stocks) {
        this.stocks = stocks;
    }
}
