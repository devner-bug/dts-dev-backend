package com.dts.challenge.hmcts_task_api.controller;

import com.dts.challenge.hmcts_task_api.dto.CreateTaskRequestDto;
import com.dts.challenge.hmcts_task_api.dto.CreateTaskResponseDto;
import com.dts.challenge.hmcts_task_api.dto.ResponseData;
import com.dts.challenge.hmcts_task_api.dto.ResponseHandler;
import com.dts.challenge.hmcts_task_api.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<ResponseData<CreateTaskResponseDto>> createTask(@RequestBody @Valid CreateTaskRequestDto request){
        return ResponseHandler.generateResponse(
                "Task created successfully",
                HttpStatus.CREATED,
                taskService.createTask(request)
        );
    }
}
