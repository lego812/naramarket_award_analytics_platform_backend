package com.example.award.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AwardResultItem {
    private String bidNtceNo;
    private String bidNtceOrd;
    private String bidClsfcNo;
    private String rbidNo;
    private String ntceDivCd;
    private String bidNtceNm;
    private Integer prtcptCnum;
    private String bidwinnrNm;
    private String bidwinnrBizno;
    private String bidwinnrCeoNm;
    private String bidwinnrAdrs;
    private String bidwinnrTelNo;
    private Long sucsfbidAmt;
    private BigDecimal sucsfbidRate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rlOpengDt;
    private String dminsttCd;
    private String dminsttNm;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rgstDt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fnlSucsfDate;
    private String fnlSucsfCorpOfcl;

    public String getBidNtceNo() {
        return bidNtceNo;
    }

    public void setBidNtceNo(String bidNtceNo) {
        this.bidNtceNo = bidNtceNo;
    }

    public String getBidNtceOrd() {
        return bidNtceOrd;
    }

    public void setBidNtceOrd(String bidNtceOrd) {
        this.bidNtceOrd = bidNtceOrd;
    }

    public String getBidClsfcNo() {
        return bidClsfcNo;
    }

    public void setBidClsfcNo(String bidClsfcNo) {
        this.bidClsfcNo = bidClsfcNo;
    }

    public String getRbidNo() {
        return rbidNo;
    }

    public void setRbidNo(String rbidNo) {
        this.rbidNo = rbidNo;
    }

    public String getNtceDivCd() {
        return ntceDivCd;
    }

    public void setNtceDivCd(String ntceDivCd) {
        this.ntceDivCd = ntceDivCd;
    }

    public String getBidNtceNm() {
        return bidNtceNm;
    }

    public void setBidNtceNm(String bidNtceNm) {
        this.bidNtceNm = bidNtceNm;
    }

    public Integer getPrtcptCnum() {
        return prtcptCnum;
    }

    public void setPrtcptCnum(Integer prtcptCnum) {
        this.prtcptCnum = prtcptCnum;
    }

    public String getBidwinnrNm() {
        return bidwinnrNm;
    }

    public void setBidwinnrNm(String bidwinnrNm) {
        this.bidwinnrNm = bidwinnrNm;
    }

    public String getBidwinnrBizno() {
        return bidwinnrBizno;
    }

    public void setBidwinnrBizno(String bidwinnrBizno) {
        this.bidwinnrBizno = bidwinnrBizno;
    }

    public String getBidwinnrCeoNm() {
        return bidwinnrCeoNm;
    }

    public void setBidwinnrCeoNm(String bidwinnrCeoNm) {
        this.bidwinnrCeoNm = bidwinnrCeoNm;
    }

    public String getBidwinnrAdrs() {
        return bidwinnrAdrs;
    }

    public void setBidwinnrAdrs(String bidwinnrAdrs) {
        this.bidwinnrAdrs = bidwinnrAdrs;
    }

    public String getBidwinnrTelNo() {
        return bidwinnrTelNo;
    }

    public void setBidwinnrTelNo(String bidwinnrTelNo) {
        this.bidwinnrTelNo = bidwinnrTelNo;
    }

    public Long getSucsfbidAmt() {
        return sucsfbidAmt;
    }

    public void setSucsfbidAmt(Long sucsfbidAmt) {
        this.sucsfbidAmt = sucsfbidAmt;
    }

    public BigDecimal getSucsfbidRate() {
        return sucsfbidRate;
    }

    public void setSucsfbidRate(BigDecimal sucsfbidRate) {
        this.sucsfbidRate = sucsfbidRate;
    }

    public LocalDateTime getRlOpengDt() {
        return rlOpengDt;
    }

    public void setRlOpengDt(LocalDateTime rlOpengDt) {
        this.rlOpengDt = rlOpengDt;
    }

    public String getDminsttCd() {
        return dminsttCd;
    }

    public void setDminsttCd(String dminsttCd) {
        this.dminsttCd = dminsttCd;
    }

    public String getDminsttNm() {
        return dminsttNm;
    }

    public void setDminsttNm(String dminsttNm) {
        this.dminsttNm = dminsttNm;
    }

    public LocalDateTime getRgstDt() {
        return rgstDt;
    }

    public void setRgstDt(LocalDateTime rgstDt) {
        this.rgstDt = rgstDt;
    }

    public LocalDate getFnlSucsfDate() {
        return fnlSucsfDate;
    }

    public void setFnlSucsfDate(LocalDate fnlSucsfDate) {
        this.fnlSucsfDate = fnlSucsfDate;
    }

    public String getFnlSucsfCorpOfcl() {
        return fnlSucsfCorpOfcl;
    }

    public void setFnlSucsfCorpOfcl(String fnlSucsfCorpOfcl) {
        this.fnlSucsfCorpOfcl = fnlSucsfCorpOfcl;
    }
}
