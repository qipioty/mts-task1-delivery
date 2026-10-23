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
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleMenu {

    private final OrderService service;
    private final Scanner scanner;

    public ConsoleMenu(OrderService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        try {
            run();
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("Ввод завершён. До свидания!");
        }
    }

    private void run() {
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
            } catch (NoSuchElementException e) {
                throw e;
            } catch (RuntimeException e) {
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
            String line = readString(prompt).trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Это не целое число. Попробуйте ещё раз.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            String line = readString(prompt).trim().replace(',', '.');
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Это не число. Попробуйте ещё раз.");
            }
        }
    }

    private Address readAddress() {
        while (true) {
            String city = readString("Город: ");
            String street = readString("Улица: ");
            String house = readString("Дом: ");
            String apartment = readString("Квартира (можно оставить пустой): ");
            try {
                return new Address(city, street, house, apartment);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Введите адрес заново.");
            }
        }
    }

    private Client readClient(Address address) {
        while (true) {
            String name = readString("Имя клиента: ");
            String phone = readString("Телефон: ");
            try {
                return service.createClient(name, phone, address);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте ещё раз.");
            }
        }
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
                case 1 -> {
                    return new StandardDelivery();
                }
                case 2 -> {
                    return new ExpressDelivery();
                }
                case 3 -> {
                    return new PickupDelivery();
                }
                default -> System.out.println("Нет такого варианта. Попробуйте ещё раз.");
            }
        }
    }

    // ---------- Вывод ----------

    private void printDeliveryInfo(Order order) {
        DeliveryMethod method = order.getDeliveryMethod();
        System.out.println("Способ доставки: " + method.getName());
        System.out.printf("Стоимость доставки: %.2f руб.%n", method.calculateCost(order));
        System.out.println("Срок: " + method.calculateDeliveryTime(order) + " дн.");
        System.out.println("Требует курьера: " + (method.requiresCourier() ? "да" : "нет"));
        System.out.printf("Итого по заказу: %.2f руб.%n", order.getTotalPrice());
    }

    private void printOrderDetails(Order order) {
        System.out.println(order);
        for (OrderItem item : order.getItems()) {
            System.out.println("  - " + item);
        }
    }

    // ---------- Пункты меню ----------

    private void addCourier() {
        System.out.println();
        System.out.println("--- Добавление курьера ---");
        while (true) {
            String name = readString("Имя курьера: ");
            String phone = readString("Телефон: ");
            try {
                service.addCourier(name, phone);
                System.out.println("Курьер добавлен.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Попробуйте ещё раз.");
            }
        }
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

        System.out.println("Адрес доставки:");
        Address address = readAddress();
        Client client = readClient(address);
        DeliveryMethod method = chooseDeliveryMethod();

        Order order = service.createOrder(client, address, method);

        System.out.println("Заказ #" + order.getId() + " создан. Добавьте хотя бы одну позицию.");
        addItemsToOrder(order);
        System.out.println();
        printOrderDetails(order);
        printDeliveryInfo(order);
    }

    private void addItemsToOrder(Order order) {
        while (true) {
            String productName = readString("Название товара (или 'стоп' для завершения): ");
            if (productName.trim().equalsIgnoreCase("стоп")) {
                if (order.getItems().isEmpty()) {
                    System.out.println("В заказе должна быть хотя бы одна позиция.");
                    continue;
                }
                break;
            }
            int quantity = readInt("Количество: ");
            double price = readDouble("Цена за единицу: ");
            try {
                order.addItem(new OrderItem(productName, quantity, price));
                System.out.println("Позиция добавлена.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Ошибка: " + e.getMessage() + ". Позиция не добавлена.");
            }
        }
    }

    private void chooseDelivery() {
        System.out.println();
        System.out.println("--- Способ доставки ---");
        int orderId = readInt("Введите id заказа: ");
        Order order = service.findOrderById(orderId);

        System.out.println("Текущие условия:");
        printDeliveryInfo(order);

        if (order.getStatus() != OrderStatus.CREATED) {
            System.out.println("Способ доставки можно менять только в новом заказе.");
            return;
        }

        DeliveryMethod method = chooseDeliveryMethod();
        order.setDeliveryMethod(method);

        System.out.println();
        System.out.println("Способ доставки изменён. Новые условия:");
        printDeliveryInfo(order);
    }

    private void assignCourier() {
        System.out.println();
        System.out.println("--- Назначение курьера ---");
        int orderId = readInt("Введите id заказа: ");
        Order order = service.findOrderById(orderId);

        if (!order.getDeliveryMethod().requiresCourier()) {
            System.out.println("Этот способ доставки не требует курьера.");
            return;
        }
        if (order.getStatus() != OrderStatus.CREATED) {
            System.out.println("Курьера можно назначить только в новом заказе. Статус: " + order.getStatus());
            return;
        }

        List<Courier> freeCouriers = service.getFreeCouriers();
        if (freeCouriers.isEmpty()) {
            System.out.println("Нет свободных курьеров. Добавьте курьера через пункт 6.");
            return;
        }

        System.out.println("Свободные курьеры:");
        for (Courier courier : freeCouriers) {
            System.out.println(courier);
        }

        int courierId = readInt("Введите id курьера: ");
        Courier chosen = service.findFreeCourierById(courierId);

        order.assignCourier(chosen);
        System.out.println("Курьер назначен.");
        printOrderDetails(order);
    }

    private void changeStatus() {
        System.out.println();
        System.out.println("--- Изменение статуса ---");
        int orderId = readInt("Введите id заказа: ");
        Order order = service.findOrderById(orderId);

        System.out.println("Текущий статус: " + order.getStatus());
        System.out.println("Статусы:");
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
        System.out.println("Статус изменён.");
        printOrderDetails(order);
    }
}