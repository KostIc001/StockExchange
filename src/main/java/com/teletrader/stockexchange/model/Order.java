package com.teletrader.stockexchange.model;

import com.teletrader.stockexchange.enums.OrderType;

public class Order {

    private Long id;
    private String userId;
    private int amount;
    private double price;
    private OrderType type; // BUY,SELL
    private String share;
    private long timestamp; // Timestamp for ordering in priority queue

    public Order(Long id, String userId, int amount, double price, OrderType type, String share) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.price = price;
        this.type = type;
        this.share = share;
        this.timestamp = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
