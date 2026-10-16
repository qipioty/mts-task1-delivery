package com.example.delivery;

import com.example.delivery.service.OrderService;
import com.example.delivery.ui.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        OrderService service = new OrderService();
        ConsoleMenu menu = new ConsoleMenu(service);
        menu.start();
    }
}