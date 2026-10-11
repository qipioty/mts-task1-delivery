package com.example.delivery.model;

public class Address {
    private String city;
    private String street;
    private String house;
    private String apartment;

    public Address (String city, String street, String house, String apartment) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
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
        return city + ", " + street + ", д. " + house + ", кв. " + apartment;
    }
}
