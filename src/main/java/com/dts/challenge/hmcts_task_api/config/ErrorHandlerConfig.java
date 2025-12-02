package com.dts.challenge.hmcts_task_api.config;

import com.dts.challenge.hmcts_task_api.dto.ResponseData;
import com.dts.challenge.hmcts_task_api.dto.ResponseError;
import com.dts.challenge.hmcts_task_api.dto.ResponseHandler;
import com.dts.challenge.hmcts_task_api.exception.DuplicateException;
import com.dts.challenge.hmcts_task_api.exception.TaskException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandlerConfig {
    @ExceptionHandler(TaskException.class)
    public ResponseEntity<ResponseData<ResponseError<String>>> handleTaskExceptionHandler(TaskException e){
        return ResponseHandler.generateResponse(
                "Task has an error",
                HttpStatus.BAD_REQUEST,
                ResponseError.<String>builder()
                        .error(e.getMsg())
                        .build()
        );
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ResponseData<ResponseError<Map<String, String>>>> handleDuplicateExceptionHandler(DuplicateException e){
        return ResponseHandler.generateResponse(
                "Duplicate entity",
                HttpStatus.CONFLICT,
                ResponseError.<Map<String, String>>builder()
                        .errors(Map.of(e.getField(), e.getMsg()))
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData<ResponseError<Map<String, String>>>> handleArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        var errors = new HashMap<String, String >();
        e.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError)error).getField();
            var errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });

        return ResponseHandler.generateResponse(
                "Validation failed",
                HttpStatus.BAD_REQUEST,
                ResponseError.<Map<String, String>>builder()
                        .errors(errors)
                        .build()
        );
    }
}
