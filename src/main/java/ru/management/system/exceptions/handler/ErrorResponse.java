package ru.management.system.exceptions.handler;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp, int status,
        String error, String message
) {
}
