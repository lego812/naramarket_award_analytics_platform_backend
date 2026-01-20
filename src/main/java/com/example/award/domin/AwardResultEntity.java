package com.example.award.domin;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_award")
public class AwardResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bidNoticeNo;

    private String bidNoticeName;

    private String bidNoticeOrd;

    private String bidClsfcNo;

    private String rbidNo;

    private String ntceDivCd;

    private String agencyCode;

    private String agencyName;

    private Integer prtcptCnum;

    private String bidwinnrCeoNm;

    private String bidwinnrAdrs;

    private String bidwinnrTelNo;

    private Long sucsfbidAmt;

    private BigDecimal sucsfbidRate;

    private LocalDateTime rlOpengDt;

    private String dminsttCd;

    private String dminsttNm;

    private LocalDateTime rgstDt;

    private LocalDate fnlSucsfDate;

    private String fnlSucsfCorpOfcl;

    private String bidwinnrBizno;

    private String bidwinnrNm;

    private boolean confirmed;

    private String workType;

    public Long getId() {
        return id;
    }

    public String getBidNoticeNo() {
        return bidNoticeNo;
    }

    public void setBidNoticeNo(String bidNoticeNo) {
        this.bidNoticeNo = bidNoticeNo;
    }

    public String getBidNoticeName() {
        return bidNoticeName;
    }

    public void setBidNoticeName(String bidNoticeName) {
        this.bidNoticeName = bidNoticeName;
    }

    public String getBidNoticeOrd() {
        return bidNoticeOrd;
    }

    public void setBidNoticeOrd(String bidNoticeOrd) {
        this.bidNoticeOrd = bidNoticeOrd;
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

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Integer getPrtcptCnum() {
        return prtcptCnum;
    }

    public void setPrtcptCnum(Integer prtcptCnum) {
        this.prtcptCnum = prtcptCnum;
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

    public String getBidwinnrBizno() {
        return bidwinnrBizno;
    }

    public void setBidwinnrBizno(String bidwinnrBizno) {
        this.bidwinnrBizno = bidwinnrBizno;
    }

    public String getBidwinnrNm() {
        return bidwinnrNm;
    }

    public void setBidwinnrNm(String bidwinnrNm) {
        this.bidwinnrNm = bidwinnrNm;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }
}
