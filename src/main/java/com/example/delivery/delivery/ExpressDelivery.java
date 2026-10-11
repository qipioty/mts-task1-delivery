package com.example.delivery.delivery;

import com.example.delivery.model.Order;

public class ExpressDelivery implements DeliveryMethod {
    @Override
    public double calculateCost(Order order) {
        return 600.0;
    }

    @Override
    public int calculateDeliveryTime(Order order) {
        return 1;
    }

    @Override
    public boolean requiresCourier() {
        return true;
    }
}