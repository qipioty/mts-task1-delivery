package com.example.delivery.delivery;

import com.example.delivery.model.Order;

public class PickupDelivery implements DeliveryMethod {
    @Override
    public double calculateCost(Order order) {
        return 0.0;
    }

    @Override
    public int calculateDeliveryTime(Order order) {
        return 0;
    }

    @Override
    public boolean requiresCourier() {
        return false;
    }
}
