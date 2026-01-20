package com.example.award.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "service_award")
@Getter
public class Award {
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

    private String linkInsttNm;


}
