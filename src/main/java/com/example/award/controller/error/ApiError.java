package com.example.award.controller.error;

import java.time.OffsetDateTime;
import java.util.Map;

public record ApiError(
        String code,
        String message,
        String path,
        OffsetDateTime timestamp,
        Map<String, String> details
) {
}
