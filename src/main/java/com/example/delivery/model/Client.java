package com.example.delivery.model;

public class Client {
    private final int id;
    private final String name;
    private final String phone;
    private final Address address;

    public Client(int id, String name, String phone, Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Адрес клиента не указан");
        }
        this.id = id;
        this.name = Validation.requireNotBlank(name, "Имя клиента");
        this.phone = Validation.requirePhone(phone);
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", тел. " + phone + ", адрес: " + address;
    }
}