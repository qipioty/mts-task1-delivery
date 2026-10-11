package com.example.delivery.model;

public class Client {
    private int id;
    private String name;
    private String phone;
    private Address address;

    public Client (int id, String name, String phone, Address address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
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
