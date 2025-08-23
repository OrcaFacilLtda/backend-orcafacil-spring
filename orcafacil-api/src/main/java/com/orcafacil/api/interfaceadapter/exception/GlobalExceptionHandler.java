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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(ConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder();
        ex.getConstraintViolations().forEach(violation -> {
            sb.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
        });

        ApiResponse<Void> response = new ApiResponse<>(false, "Erro de validação: " + sb.toString(), null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiResponse<Void>> handleDomainException(DomainException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, "Erro interno do servidor: " + ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
