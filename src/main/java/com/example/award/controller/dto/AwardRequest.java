package com.example.award.controller.dto;

import com.example.award.service.dto.AwardCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AwardRequest (
    @NotBlank(message = "시작일은 필수입니다.")
    @Pattern(regexp = "\\d{12}", message = "시작일 형식은 yyyyMMddHHmm 이어야 합니다.")
    String inqryBgnDt,
    @NotBlank(message = "마감일은 필수입니다.")
    @Pattern(regexp = "\\d{12}", message = "마감일 형식은 yyyyMMddHHmm 이어야 합니다.")
    String inqryEndDt,
    @NotBlank(message = "수요기관명은 필수입니다.")
    String dminsttNm,
    @NotBlank(message = "입찰공고명은 필수입니다.")
    String bidNtceNm
    ){
    public AwardCommand toCommand() {
        return new AwardCommand(
                inqryBgnDt,
                inqryEndDt,
                dminsttNm,
                bidNtceNm
        );
    }
}
