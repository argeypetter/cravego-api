package com.cravego.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiError {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> errors;
}
