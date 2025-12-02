package com.dts.challenge.hmcts_task_api.controller;

import com.dts.challenge.hmcts_task_api.dto.CreateTaskRequestDto;
import com.dts.challenge.hmcts_task_api.mapper.TaskMapper;
import com.dts.challenge.hmcts_task_api.model.TaskStatus;
import com.dts.challenge.hmcts_task_api.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskMapper taskMapper;

    private static final String BASE_URL = "/api/v1/task/";

    @BeforeEach
    void setup() {
    }

    @Test
    void shouldCreateTask() throws Exception {
        var request = new CreateTaskRequestDto("Test title", "This is the description of a new task", LocalDateTime.now());

        mockMvc.perform(
                post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Task created successfully"));
    }

    @Test
    void shouldNotCreateTask_DoNotAllowDuplicate() throws Exception {

        var request = new CreateTaskRequestDto("Create a new task", "This is the description of a new task", LocalDateTime.now());

        // Insert a task with the duplicate title
        var task = taskMapper.toTask(request);
        task.setStatus(TaskStatus.OPEN);
        taskRepository.save(task);

        mockMvc.perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Duplicate entity"))
                .andExpect(jsonPath("$.data.errors.title").value("Task with title already exists"));
    }

    @Test
    void shouldNotCreateTask_ReturnErrorWhenRequestIsEmpty() throws Exception {
        var request = CreateTaskRequestDto.builder().build();

        mockMvc.perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.data.errors.title").value("Task is a required field"))
                .andExpect(jsonPath("$.data.errors.due").value("Due date is a required field"));
    }
}