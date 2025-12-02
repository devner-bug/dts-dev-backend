package com.dts.challenge.hmcts_task_api.service;

import com.dts.challenge.hmcts_task_api.dto.CreateTaskRequestDto;
import com.dts.challenge.hmcts_task_api.dto.CreateTaskResponseDto;
import com.dts.challenge.hmcts_task_api.exception.DuplicateException;
import com.dts.challenge.hmcts_task_api.mapper.TaskMapper;
import com.dts.challenge.hmcts_task_api.model.TaskStatus;
import com.dts.challenge.hmcts_task_api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    public CreateTaskResponseDto createTask(CreateTaskRequestDto request){
        if(taskRepository.existsByTitleIgnoreCase(request.title())){
            throw new DuplicateException("title", "Task with title already exists");
        }

        var task = taskMapper.toTask(request);
        task.setStatus(TaskStatus.OPEN);// Auto set the task status on create. Do not rely on client request
        var response = taskRepository.save(task);

        return taskMapper.toTaskResponse(response);
    }
}
