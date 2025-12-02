package com.dts.challenge.hmcts_task_api.dto;

import com.dts.challenge.hmcts_task_api.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateTaskResponseDto(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime due
) {
}
