package ru.management.system.exceptions;

public class TaskIsNotUniqueException extends RuntimeException {
    public TaskIsNotUniqueException(String message) {
        super(message);
    }
}
