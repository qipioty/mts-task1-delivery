package com.example.delivery.model;

public class Courier {
    private final int id;
    private final String name;
    private final String phone;
    private boolean available;

    public Courier(int id, String name, String phone, boolean available) {
        this.id = id;
        this.name = Validation.requireNotBlank(name, "Имя курьера");
        this.phone = Validation.requirePhone(phone);
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

    // Package-private: only Order may change courier availability
    void setBusy() {
        this.available = false;
    }

    void setFree() {
        this.available = true;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", тел. " + phone + ", " + (available ? "свободен" : "занят");
    }
}