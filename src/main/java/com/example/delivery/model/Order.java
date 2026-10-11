package com.example.delivery.model;

import com.example.delivery.delivery.DeliveryMethod;

import java.util.ArrayList;
import java.util.List;



public class Order {
    private int id;
    private Client client;
    private Address address;
    private List<OrderItem> items;
    private DeliveryMethod deliveryMethod;
    private Courier courier;
    private OrderStatus status;

    public Order(int id, Client client, Address address, DeliveryMethod deliveryMethod) {
        this.id = id;
        this.client = client;
        this.address = address;
        this.deliveryMethod = deliveryMethod;
        this.items = new ArrayList<>();
        this.courier = null;
        this.status = OrderStatus.CREATED;
    }

    public void addItem(OrderItem item) {
        if (status == OrderStatus.CANCELLED || status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Нельзя добавить позицию в заказ со статусом " + status);
        }
        items.add(item);
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getTotalPrice() {
        double itemsTotal = 0;
        for (OrderItem item : items) {
            itemsTotal += item.getTotal();
        }
        return itemsTotal + deliveryMethod.calculateCost(this);
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Способ доставки можно менять только в новом заказе");
        }
        this.deliveryMethod = deliveryMethod;
    }

    public void assignCourier(Courier courier) {
        if (!deliveryMethod.requiresCourier()) {
            throw new IllegalStateException("Этот способ доставки не требует курьера");
        }
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Курьера можно назначить только в новом заказе");
        }
        this.courier = courier;
        this.status = OrderStatus.COURIER_ASSIGNED;
        courier.setBusy();
    }

    public void changeStatus(OrderStatus newStatus) {
        boolean allowed = switch (status) {
            case CREATED -> newStatus == OrderStatus.COURIER_ASSIGNED
                    || newStatus == OrderStatus.CANCELLED;
            case COURIER_ASSIGNED -> newStatus == OrderStatus.IN_DELIVERY
                    || newStatus == OrderStatus.CANCELLED;
            case IN_DELIVERY -> newStatus == OrderStatus.DELIVERED;
            case DELIVERED, CANCELLED -> false;
        };

        if (!allowed) {
            throw new IllegalStateException(
                    "Нельзя перевести заказ из " + status + " в " + newStatus
            );
        }

        this.status = newStatus;
    }

    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Address getAddress() {
        return address;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public Courier getCourier() {
        return courier;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Заказ #" + id +
                " | клиент: " + client.getName() +
                " | адрес: " + address +
                " | позиций: " + items.size() +
                " | доставка: " + deliveryMethod.getClass().getSimpleName() +
                " | статус: " + status;
    }
}