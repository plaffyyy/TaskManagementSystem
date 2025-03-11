package ru.management.system.entities.task;

import ru.management.system.exceptions.UndefinedPriorityException;
import ru.management.system.exceptions.UndefinedStatusException;

public enum Priority {
    LOW,
    MEDIUM,
    HARD;
    public static Priority priorityFromString(String priority) {
        return switch (priority) {
            case ("low") -> Priority.LOW;
            case ("medium") -> Priority.MEDIUM;
            case ("hard") -> Priority.HARD;
            default -> throw new UndefinedPriorityException("Нет такого приоритета");
        };
    }
}
