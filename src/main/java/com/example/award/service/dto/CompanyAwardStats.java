package com.example.award.service.dto;

import java.util.List;

public record CompanyAwardStats(
        String bidwinnrNm,
        String bidwinnrBizno,
        Integer winCount,
        List<InstitutionRank> topInstitutions
) {
}
