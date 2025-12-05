package com.dts.challenge.hmcts_task_api.service;

import com.dts.challenge.hmcts_task_api.dto.CreateTaskRequestDto;
import com.dts.challenge.hmcts_task_api.dto.CreateTaskResponseDto;
import com.dts.challenge.hmcts_task_api.exception.DuplicateException;
import com.dts.challenge.hmcts_task_api.mapper.TaskMapper;
import com.dts.challenge.hmcts_task_api.model.Task;
import com.dts.challenge.hmcts_task_api.model.TaskStatus;
import com.dts.challenge.hmcts_task_api.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;
    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_shouldSaveTask(){
        var id = UUID.randomUUID();
        var statusOpen = TaskStatus.OPEN;
        var request = new CreateTaskRequestDto("Test title", "Test Description", LocalDateTime.now());
        var output = new CreateTaskResponseDto(id, request.title(), request.description(), statusOpen, request.due());
        var task = new Task(id, request.title(), request.description(), statusOpen, request.due());

        when(taskMapper.toTask(request)).thenReturn(task);
        when(taskRepository.save(any())).thenReturn(any());
        when(taskMapper.toTaskResponse(task)).thenReturn(output);

        var result = taskService.createTask(request);

        // Then
        assertTrue(nonNull(result.id()));
        assertEquals(id, result.id());
        assertEquals("Test title", result.title());
        assertEquals("Test Description", result.description());
        assertEquals(statusOpen, result.status());
    }

    @Test
    void createTask_ShouldNotSaveDuplicateTaskIgnoreCase(){
        var request = new CreateTaskRequestDto("Test title 1", "Test Description 1", LocalDateTime.now());

        when(taskRepository.existsByTitleIgnoreCase(request.title())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> {
            taskService.createTask(request);
        });
    }
}