package com.example.award.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.award.domain.Award;
import com.fasterxml.jackson.annotation.JsonFormat;

public record AwardInfo(
        String bidNtceNo,
        String bidNtceOrd,
        String bidClsfcNo,
        String rbidNo,
        String ntceDivCd,
        String bidNtceNm,
        Integer prtcptCnum,
        String bidwinnrNm,
        String bidwinnrBizno,
        String bidwinnrCeoNm,
        String bidwinnrAdrs,
        String bidwinnrTelNo,
        Long sucsfbidAmt,
        BigDecimal sucsfbidRate,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime rlOpengDt,
        String dminsttCd,
        String dminsttNm,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime rgstDt,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate fnlSucsfDate,
        String fnlSucsfCorpOfcl,
        String linkInsttNm
    ){
    public AwardInfo from(Award result){
        return new AwardInfo(
                result.getBidNoticeNo(),
                result.getBidNoticeOrd(),
                result.getBidClsfcNo(),
                result.getRbidNo(),
                result.getNtceDivCd(),
                result.getBidNoticeName(),
                result.getPrtcptCnum(),
                result.getBidwinnrNm(),
                result.getBidwinnrBizno(),
                result.getBidwinnrCeoNm(),
                result.getBidwinnrAdrs(),
                result.getBidwinnrTelNo(),
                result.getSucsfbidAmt(),
                result.getSucsfbidRate(),
                result.getRlOpengDt(),
                result.getDminsttCd(),
                result.getDminsttNm(),
                result.getRgstDt(),
                result.getFnlSucsfDate(),
                result.getFnlSucsfCorpOfcl(),
                result.getLinkInsttNm()
        );
    }
}
