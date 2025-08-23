package com.orcafacil.api.interfaceadapter.exception;

import com.orcafacil.api.domain.exception.DomainException;
import com.orcafacil.api.interfaceadapter.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {
}
