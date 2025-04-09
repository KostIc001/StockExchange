package com.teletrader.stockexchange.model;

public class Trade {

    private String buyerId;
    private String sellerId;
    private double price;
    private int amount;
    private long timestamp;

    public Trade(String buyerId, String sellerId, double price, int amount, long timestamp) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
