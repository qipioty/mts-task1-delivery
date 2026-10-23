package com.example.delivery.model;

import com.example.delivery.delivery.DeliveryMethod;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int id;
    private final Client client;
    private final Address address;
    private final List<OrderItem> items;
    private DeliveryMethod deliveryMethod;
    private Courier courier;
    private OrderStatus status;

    public Order(int id, Client client, Address address, DeliveryMethod deliveryMethod) {
        if (client == null) {
            throw new IllegalArgumentException("Клиент не указан");
        }
        if (address == null) {
            throw new IllegalArgumentException("Адрес доставки не указан");
        }
        if (deliveryMethod == null) {
            throw new IllegalArgumentException("Способ доставки не указан");
        }
        this.id = id;
        this.client = client;
        this.address = address;
        this.deliveryMethod = deliveryMethod;
        this.items = new ArrayList<>();
        this.courier = null;
        this.status = OrderStatus.CREATED;
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Позиция заказа не указана");
        }
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Нельзя добавить позицию в заказ со статусом " + status);
        }
        items.add(item);
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getItemsTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotal();
        }
        return total;
    }

    public double getDeliveryCost() {
        return deliveryMethod.calculateCost(this);
    }

    public double getTotalPrice() {
        return getItemsTotal() + getDeliveryCost();
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        if (deliveryMethod == null) {
            throw new IllegalArgumentException("Способ доставки не указан");
        }
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Способ доставки можно менять только в новом заказе");
        }
        this.deliveryMethod = deliveryMethod;
    }

    public void assignCourier(Courier courier) {
        if (courier == null) {
            throw new IllegalArgumentException("Курьер не указан");
        }
        if (!deliveryMethod.requiresCourier()) {
            throw new IllegalStateException("Этот способ доставки не требует курьера");
        }
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Курьера можно назначить только в новом заказе");
        }
        if (items.isEmpty()) {
            throw new IllegalStateException("Нельзя назначить курьера на заказ без позиций");
        }
        if (!courier.isAvailable()) {
            throw new IllegalStateException("Курьер " + courier.getName() + " занят");
        }
        this.courier = courier;
        this.status = OrderStatus.COURIER_ASSIGNED;
        courier.setBusy();
    }

    public void changeStatus(OrderStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Статус не указан");
        }
        if (newStatus == OrderStatus.COURIER_ASSIGNED) {
            throw new IllegalStateException(
                    "Статус COURIER_ASSIGNED устанавливается только при назначении курьера");
        }

        boolean allowed = switch (status) {
            case CREATED -> newStatus == OrderStatus.CANCELLED
                    || (newStatus == OrderStatus.IN_DELIVERY && !deliveryMethod.requiresCourier());
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
        if (newStatus == OrderStatus.IN_DELIVERY && items.isEmpty()) {
            throw new IllegalStateException("Нельзя начать доставку заказа без позиций");
        }

        this.status = newStatus;

        boolean finished = newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.CANCELLED;
        if (finished && courier != null) {
            courier.setFree();
        }
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
        return String.format(
                "Заказ #%d | клиент: %s | адрес: %s | позиций: %d | доставка: %s | курьер: %s | итого: %.2f руб. | статус: %s",
                id,
                client.getName(),
                address,
                items.size(),
                deliveryMethod.getName(),
                courier == null ? "не назначен" : courier.getName(),
                getTotalPrice(),
                status);
    }
}