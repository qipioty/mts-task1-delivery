package com.example.delivery.delivery;

import com.example.delivery.model.Order;

public interface DeliveryMethod {
    double calculateCost(Order order);
    int calculateDeliveryTime(Order order);
    boolean requiresCourier();
}