package com.example.delivery.ui;

import com.example.delivery.delivery.DeliveryMethod;
import com.example.delivery.delivery.ExpressDelivery;
import com.example.delivery.delivery.PickupDelivery;
import com.example.delivery.delivery.StandardDelivery;
import com.example.delivery.model.Address;
import com.example.delivery.model.Client;
import com.example.delivery.model.Courier;
import com.example.delivery.model.Order;
import com.example.delivery.model.OrderItem;
import com.example.delivery.model.OrderStatus;
import com.example.delivery.service.OrderService;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private final OrderService service;
    private final Scanner scanner;

    public ConsoleMenu(OrderService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            printMenu();
            int choice = readInt("Выберите пункт: ");

            try {
                switch (choice) {
                    case 1 -> createOrder();
                    case 2 -> showOrders();
                    case 3 -> chooseDelivery();
                    case 4 -> assignCourier();
                    case 5 -> changeStatus();
                    case 6 -> addCourier();
                    case 0 -> {
                        System.out.println("До свидания!");
                        return;
                    }
                    default -> System.out.println("Нет такого пункта.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=== Служба доставки ===");
        System.out.println("1. Создать заказ");
        System.out.println("2. Показать список заказов");
        System.out.println("3. Выбрать способ доставки");
        System.out.println("4. Назначить курьера");
        System.out.println("5. Изменить статус заказа");
        System.out.println("6. Добавить курьера");
        System.out.println("0. Выход");
    }

    // ---------- Ввод ----------

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Это не число. Попробуйте ещё раз.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Это не число. Попробуйте ещё раз.");
            }
        }
    }

    // ---------- Пункты меню ----------

    private void addCourier() {
        System.out.println();
        System.out.println("--- Добавление курьера ---");
        String name = readString("Имя курьера: ");
        String phone = readString("Телефон: ");
        service.addCourier(name, phone);
        System.out.println("Курьер добавлен.");
    }

    private void showOrders() {
        System.out.println();
        System.out.println("--- Список заказов ---");
        List<Order> orders = service.getOrders();
        if (orders.isEmpty()) {
            System.out.println("Заказов пока нет.");
            return;
        }
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    private void createOrder() {
        System.out.println();
        System.out.println("--- Новый заказ ---");

        String name = readString("Имя клиента: ");
        String phone = readString("Телефон: ");
        String city = readString("Город: ");
        String street = readString("Улица: ");
        String house = readString("Дом: ");
        String apartment = readString("Квартира: ");

        Address address = new Address(city, street, house, apartment);
        Client client = new Client(0, name, phone, address);
        DeliveryMethod method = chooseDeliveryMethod();

        Order order = service.createOrder(client, address, method);

        System.out.println("Заказ создан: " + order);
        System.out.println("Добавьте хотя бы одну позицию.");
        addItemsToOrder(order);
    }

    private DeliveryMethod chooseDeliveryMethod() {
        System.out.println();
        System.out.println("Выберите способ доставки:");
        System.out.println("1. Стандартная (курьер)");
        System.out.println("2. Экспресс (курьер)");
        System.out.println("3. Самовывоз");

        while (true) {
            int choice = readInt("Ваш выбор: ");
            switch (choice) {
                case 1 -> { return new StandardDelivery(); }
                case 2 -> { return new ExpressDelivery(); }
                case 3 -> { return new PickupDelivery(); }
                default -> System.out.println("Нет такого варианта. Попробуйте ещё раз.");
            }
        }
    }

    private void addItemsToOrder(Order order) {
        while (true) {
            String productName = readString("Название товара (или 'стоп' для завершения): ");
            if (productName.equalsIgnoreCase("стоп")) {
                break;
            }
            int quantity = readInt("Количество: ");
            double price = readDouble("Цена за единицу: ");
            order.addItem(new OrderItem(productName, quantity, price));
        }
        System.out.println("Итоговая стоимость заказа: " + order.getTotalPrice() + " руб.");
    }

    private void chooseDelivery() {
        System.out.println();
        System.out.println("--- Стоимость и срок доставки ---");
        int orderId = readInt("Введите id заказа: ");
        Order order = service.findOrderById(orderId);

        DeliveryMethod method = order.getDeliveryMethod();
        System.out.println("Способ доставки: " + method.getClass().getSimpleName());
        System.out.println("Стоимость доставки: " + method.calculateCost(order) + " руб.");
        System.out.println("Срок: " + method.calculateDeliveryTime(order) + " дн.");
        System.out.println("Требует курьера: " + (method.requiresCourier() ? "да" : "нет"));
    }

    private void assignCourier() {
        System.out.println();
        System.out.println("--- Назначение курьера ---");
        int orderId = readInt("Введите id заказа: ");
        Order order = service.findOrderById(orderId);

        List<Courier> freeCouriers = service.getFreeCouriers();
        if (freeCouriers.isEmpty()) {
            System.out.println("Нет свободных курьеров.");
            return;
        }

        System.out.println("Свободные курьеры:");
        for (Courier courier : freeCouriers) {
            System.out.println(courier);
        }

        int courierId = readInt("Введите id курьера: ");
        Courier chosen = null;
        for (Courier courier : freeCouriers) {
            if (courier.getId() == courierId) {
                chosen = courier;
                break;
            }
        }
        if (chosen == null) {
            System.out.println("Курьер с таким id не найден или занят.");
            return;
        }

        order.assignCourier(chosen);
        System.out.println("Курьер назначен. Статус заказа: " + order.getStatus());
    }

    private void changeStatus() {
        System.out.println();
        System.out.println("--- Изменение статуса ---");
        int orderId = readInt("Введите id заказа: ");
        Order order = service.findOrderById(orderId);

        System.out.println("Текущий статус: " + order.getStatus());
        System.out.println("Доступные статусы:");
        for (OrderStatus status : OrderStatus.values()) {
            System.out.println("  " + status);
        }

        String input = readString("Введите новый статус: ").trim().toUpperCase();
        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Нет такого статуса.");
            return;
        }

        order.changeStatus(newStatus);
        System.out.println("Статус изменён: " + order.getStatus());
    }
}