package ru.management.system.entities.task;

import lombok.Getter;
import ru.management.system.exceptions.UndefinedStatusException;

@Getter
public enum Status {
    EXECUTED,
    FINISHED,
    NOT_STARTED;
    public static Status statusFromString(String status) {
        return switch (status.toLowerCase()) {
            case ("executed") -> Status.EXECUTED;
            case ("finished") -> Status.FINISHED;
            case ("not started") -> Status.NOT_STARTED;
            default -> throw new UndefinedStatusException("Нет такого статуса");
        };
    }
}
