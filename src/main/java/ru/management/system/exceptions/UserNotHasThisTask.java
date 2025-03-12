package ru.management.system.exceptions;

public class UserNotHasThisTask extends RuntimeException {
    public UserNotHasThisTask(String message) {
        super(message);
    }
}
