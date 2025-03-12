package ru.management.system.entities.task;

import ru.management.system.exceptions.UndefinedPriorityException;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH;
    public static Priority priorityFromString(String priority) {
        return switch (priority.toLowerCase()) {
            case ("low") -> Priority.LOW;
            case ("medium") -> Priority.MEDIUM;
            case ("high") -> Priority.HIGH;
            default -> throw new UndefinedPriorityException("Нет такого приоритета");
        };
    }
}
