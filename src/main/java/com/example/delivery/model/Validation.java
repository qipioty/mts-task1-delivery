package com.example.delivery.model;

final class Validation {

    private Validation() {
    }

    static String requireNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Поле «" + fieldName + "» не может быть пустым");
        }
        return value.trim();
    }

    static String requirePhone(String value) {
        String phone = requireNotBlank(value, "Телефон");
        if (!phone.matches("\\+?[\\d\\s\\-()]{5,20}")) {
            throw new IllegalArgumentException("Некорректный номер телефона: " + phone);
        }
        return phone;
    }
}