package com.ss.excel.processing.service;

public enum TaskStatus {
    PENDING("Обрабатывается"),
    COMPLETED("Готово"),
    ERROR("Ошибка"),
    NOT_FOUND("Не найдено");

    private final String message;

    TaskStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
