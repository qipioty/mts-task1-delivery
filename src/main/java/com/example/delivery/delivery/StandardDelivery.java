package com.example.delivery.delivery;

import com.example.delivery.model.Order;

public class StandardDelivery implements DeliveryMethod {
    @Override
    public double calculateCost(Order order) {
        return 300.0;
    }

    @Override
    public int calculateDeliveryTime(Order order) {
        return 3;
    }

    @Override
    public boolean requiresCourier() {
        return true;
    }
}
