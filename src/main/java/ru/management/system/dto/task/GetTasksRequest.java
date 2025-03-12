package ru.management.system.dto.task;

public record GetTasksRequest(
        int indexOfPage,
        int itemsPerPage,
        String sortBy,
        String email
) {
}
