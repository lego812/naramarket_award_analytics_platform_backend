package com.example.award.service.dto;

import lombok.Builder;
import lombok.Getter;


public record AwardCommand(
        String inqryBgnDt,
        String inqryEndDt,
        String dminsttNm,
        String bidNtceNm
) {
}
