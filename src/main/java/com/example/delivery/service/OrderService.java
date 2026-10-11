package com.example.delivery.service;

import com.example.delivery.delivery.DeliveryMethod;
import com.example.delivery.model.Address;
import com.example.delivery.model.Client;
import com.example.delivery.model.Courier;
import com.example.delivery.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private List<Order> orders = new ArrayList<>();
    private List<Courier> couriers = new ArrayList<>();
    private int nextOrderId = 1;
    private int nextCourierId = 1;

    public Order createOrder(Client client, Address address, DeliveryMethod deliveryMethod) {
        int id = nextOrderId++;
        Order order = new Order(id, client, address, deliveryMethod);
        orders.add(order);
        return order;
    }

    public Courier addCourier(String name, String phone) {
        int id = nextCourierId++;
        Courier courier = new Courier(id, name, phone, true);
        couriers.add(courier);
        return courier;
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public List<Courier> getCouriers() {
        return new ArrayList<>(couriers);
    }

    public Order findOrderById(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        throw new IllegalArgumentException("Заказ с id=" + id + " не найден");
    }

    public List<Courier> getFreeCouriers() {
        List<Courier> free = new ArrayList<>();
        for (Courier courier : couriers) {
            if (courier.isAvailable()) {
                free.add(courier);
            }
        }
        return free;
    }
}