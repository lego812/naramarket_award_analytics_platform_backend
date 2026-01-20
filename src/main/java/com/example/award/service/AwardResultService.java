package com.example.award.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.award.controller.dto.AwardResultItem;
import com.example.award.domin.AwardResultEntity;
import com.example.award.repository.AwardResultRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AwardResultService {
    private final AwardResultRepository awardResultRepository;

    public AwardResultService(AwardResultRepository awardResultRepository) {
        this.awardResultRepository = awardResultRepository;
    }

    @Transactional
    public List<AwardResultEntity> saveAll(List<AwardResultItem> items) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }
        List<AwardResultEntity> entities = new ArrayList<>(items.size());
        for (AwardResultItem item : items) {
            entities.add(toEntity(item));
        }
        return awardResultRepository.saveAll(entities);
    }

    private AwardResultEntity toEntity(AwardResultItem item) {
        AwardResultEntity entity = new AwardResultEntity();
        entity.setBidNoticeNo(item.getBidNtceNo());
        entity.setBidNoticeName(item.getBidNtceNm());
        entity.setBidNoticeOrd(item.getBidNtceOrd());
        entity.setBidClsfcNo(item.getBidClsfcNo());
        entity.setRbidNo(item.getRbidNo());
        entity.setNtceDivCd(item.getNtceDivCd());
        entity.setPrtcptCnum(item.getPrtcptCnum());
        entity.setBidwinnrNm(item.getBidwinnrNm());
        entity.setBidwinnrBizno(item.getBidwinnrBizno());
        entity.setBidwinnrCeoNm(item.getBidwinnrCeoNm());
        entity.setBidwinnrAdrs(item.getBidwinnrAdrs());
        entity.setBidwinnrTelNo(item.getBidwinnrTelNo());
        entity.setSucsfbidAmt(item.getSucsfbidAmt());
        entity.setSucsfbidRate(item.getSucsfbidRate());
        entity.setRlOpengDt(item.getRlOpengDt());
        entity.setDminsttCd(item.getDminsttCd());
        entity.setDminsttNm(item.getDminsttNm());
        entity.setRgstDt(item.getRgstDt());
        entity.setFnlSucsfDate(item.getFnlSucsfDate());
        entity.setFnlSucsfCorpOfcl(item.getFnlSucsfCorpOfcl());
        return entity;
    }
}
