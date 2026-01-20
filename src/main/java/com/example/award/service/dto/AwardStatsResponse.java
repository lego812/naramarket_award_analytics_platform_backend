package com.example.award.service.dto;

import java.util.List;

public record AwardStatsResponse(
        String resultCode,
        String resultMsg,
        Integer totalCount,
        List<CompanyAwardStats> companies
) {
}
