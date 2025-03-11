package ru.management.system.entities.task;

import lombok.Getter;

@Getter
public enum Status {
    EXECUTED,
    FINISHED,
    NOT_STARTED;
}
