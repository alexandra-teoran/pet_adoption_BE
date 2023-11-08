package com.example.pet_adoption.exceptions;

import com.example.pet_adoption.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseDto> handleConflictRuntimeException(RuntimeException exception){

        ExceptionResponseDto responseDto = new ExceptionResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong",
                "An internal server issue occurred"
        );

        return ResponseEntity.ok().body(responseDto);
    }

}
