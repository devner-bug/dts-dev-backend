package com.dts.challenge.hmcts_task_api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TaskException extends RuntimeException{
    private String msg;
}
