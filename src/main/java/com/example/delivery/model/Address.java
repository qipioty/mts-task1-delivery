package com.example.delivery.model;

public class Address {
    private final String city;
    private final String street;
    private final String house;
    private final String apartment;

    public Address(String city, String street, String house, String apartment) {
        this.city = Validation.requireNotBlank(city, "Город");
        this.street = Validation.requireNotBlank(street, "Улица");
        this.house = Validation.requireNotBlank(house, "Дом");
        this.apartment = apartment == null ? "" : apartment.trim();
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getApartment() {
        return apartment;
    }

    @Override
    public String toString() {
        String result = city + ", " + street + ", д. " + house;
        if (!apartment.isEmpty()) {
            result += ", кв. " + apartment;
        }
        return result;
    }
}