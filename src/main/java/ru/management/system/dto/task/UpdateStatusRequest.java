package ru.management.system.dto.task;

import jakarta.validation.constraints.Size;

public record UpdateStatusRequest(
        @Size(max = 255, message = "Название задачи не должно быть больше 255 символов")
        String name,
        String newStatus
) {
}
