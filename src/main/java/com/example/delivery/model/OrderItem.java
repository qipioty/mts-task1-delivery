package com.example.delivery.model;

public class OrderItem {
    private String productName;
    private int quantity;
    private double price;

    public OrderItem (String productName, int quantity, double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotal() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return productName + " x" + quantity + " = " + price + " руб., итого: " + getTotal() + " руб.";
    }
}
