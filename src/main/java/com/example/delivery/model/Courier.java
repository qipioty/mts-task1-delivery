package com.example.delivery.model;

public class Courier {
    private int id;
    private String name;
    private String phone;
    private boolean available;

    public Courier(int id, String name, String phone, boolean available) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.available = available;
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

    public boolean isAvailable() {
        return available;
    }

    public void setBusy() {
        this.available = false;
    }

    public void setFree() {
        this.available = true;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", тел. " + phone + ", " + (available ? "свободен" : "занят");
    }
}