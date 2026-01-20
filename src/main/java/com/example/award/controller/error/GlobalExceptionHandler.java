package com.example.award.controller.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> details = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            details.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        String code = resolveValidationCode(details);
        ApiError error = new ApiError(
                code,
                "요청 값 검증에 실패했습니다.",
                request.getRequestURI(),
                OffsetDateTime.now(),
                details
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {
        Map<String, String> details = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String path = violation.getPropertyPath() == null ? "unknown" : violation.getPropertyPath().toString();
            details.put(path, violation.getMessage());
        });

        ApiError error = new ApiError(
                "VALIDATION_ERROR",
                "요청 값 검증에 실패했습니다.",
                request.getRequestURI(),
                OffsetDateTime.now(),
                details
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        String code = "BAD_REQUEST";
        String message = "요청 값이 올바르지 않습니다.";
        if (ex.getMessage() != null) {
            if (ex.getMessage().contains("before or equal")) {
                code = "DATE_RANGE_INVALID";
                message = "시작일은 마감일보다 이전이거나 같아야 합니다.";
            } else if (ex.getMessage().contains("5 years")) {
                code = "DATE_RANGE_TOO_LONG";
                message = "조회 기간은 최대 5년까지 허용됩니다.";
            }
        }

        ApiError error = new ApiError(
                code,
                message,
                request.getRequestURI(),
                OffsetDateTime.now(),
                Map.of()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        ApiError error = new ApiError(
                "MALFORMED_JSON",
                "요청 본문이 올바른 JSON 형식이 아닙니다.",
                request.getRequestURI(),
                OffsetDateTime.now(),
                Map.of()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(
            Exception ex,
            HttpServletRequest request
    ) {
        logger.error("Unhandled exception", ex);
        ApiError error = new ApiError(
                "INTERNAL_SERVER_ERROR",
                "예상치 못한 서버 오류가 발생했습니다.",
                request.getRequestURI(),
                OffsetDateTime.now(),
                Map.of()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private String resolveValidationCode(Map<String, String> details) {
        for (String message : details.values()) {
            if (message == null) {
                continue;
            }
            if (message.contains("필수")) {
                return "VALIDATION_REQUIRED";
            }
            if (message.contains("형식")) {
                return "VALIDATION_FORMAT";
            }
        }
        return "VALIDATION_ERROR";
    }
}
