package com.dts.challenge.hmcts_task_api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateTaskRequestDto(
        @NotEmpty(message = "Task is a required field") String title,
        String description,
        @NotNull(message = "Due date is a required field") LocalDateTime due
) {
}
