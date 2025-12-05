package com.dts.challenge.hmcts_task_api.mapper;

import com.dts.challenge.hmcts_task_api.dto.CreateTaskRequestDto;
import com.dts.challenge.hmcts_task_api.dto.CreateTaskResponseDto;
import com.dts.challenge.hmcts_task_api.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task toTask(CreateTaskRequestDto request){
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .due(request.due())
                .build();
    }

    public CreateTaskResponseDto toTaskResponse(Task response) {
        return new CreateTaskResponseDto(
                response.getId(),
                response.getTitle(),
                response.getDescription(),
                response.getStatus(),
                response.getDue()
        );
    }
}
