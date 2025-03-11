package ru.management.system.exceptions;

public class UndefinedStatusException extends RuntimeException {
    public UndefinedStatusException(String message) {
        super(message);
    }
}
