package com.dts.challenge.hmcts_task_api.repository;

import com.dts.challenge.hmcts_task_api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query(value = """ 
              SELECT EXISTS (
              SELECT 1
              FROM task
              WHERE lower(title) = lower(:title)
            );
            """, nativeQuery = true)
    boolean existsByTitleIgnoreCase(String title);
}
