package com.example.award.controller;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.example.award.controller.dto.AwardRequest;
import com.example.award.controller.dto.AwardResponse;
import com.example.award.controller.dto.AwardStoreResponse;
import com.example.award.service.AwardApiClient;
import com.example.award.service.AwardResultService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/award")
public class AwardSummaryController {

    // 조달청api 호출 후 다시 반환(한달 단위로만 검색가능)

    // List<낙찰정보> 가지고 업체 기준으로 낙찰횟수와, 수요기관 중 해당 업체에 가장 많이 낙찰받은 횟수를 계산

    private static final DateTimeFormatter QUERY_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    private final AwardApiClient awardApiClient;
    private final AwardResultService awardResultService;
    private final String defaultInqryDiv;

    public AwardSummaryController(
            AwardApiClient awardApiClient,
            AwardResultService awardResultService,
            @Value("${award.api.inqry-div}") String defaultInqryDiv) {
        this.awardApiClient = awardApiClient;
        this.awardResultService = awardResultService;
        this.defaultInqryDiv = defaultInqryDiv;
    }

    @GetMapping("/results")
    public AwardResponse getAwardResults(@ModelAttribute AwardRequest request) {
        validateRequest(request);
        return awardApiClient.fetchAwardResults(request);
    }

    @PostMapping("/results/store")
    public AwardStoreResponse storeAwardResults(@ModelAttribute AwardRequest request) {
        validateRequest(request);
        AwardResponse response = awardApiClient.fetchAwardResults(request);
        int savedCount = awardResultService.saveAll(response.getItems()).size();

        AwardStoreResponse storeResponse = new AwardStoreResponse();
        storeResponse.setResultCode(response.getResultCode());
        storeResponse.setResultMsg(response.getResultMsg());
        storeResponse.setSavedCount(savedCount);
        storeResponse.setTotalCount(response.getTotalCount());
        storeResponse.setPageNo(response.getPageNo());
        return storeResponse;
    }

    private void validateRequest(AwardRequest request) {
        String inqryDiv = request.getInqryDiv();
        if (inqryDiv == null || inqryDiv.isBlank()) {
            inqryDiv = defaultInqryDiv;
        }

        if ("3".equals(inqryDiv)) {
            if (isBlank(request.getBidNtceNo())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "bidNtceNo is required when inqryDiv=3");
            }
            return;
        }

        if (isBlank(request.getInqryBgnDt()) || isBlank(request.getInqryEndDt())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "inqryBgnDt and inqryEndDt are required");
        }

        LocalDateTime start = parseQueryDateTime(request.getInqryBgnDt(), "inqryBgnDt");
        LocalDateTime end = parseQueryDateTime(request.getInqryEndDt(), "inqryEndDt");
        if (end.isBefore(start)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "inqryEndDt must be after inqryBgnDt");
        }
        if (!YearMonth.from(start).equals(YearMonth.from(end))) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "inqryBgnDt and inqryEndDt must be within the same month");
        }
    }

    private LocalDateTime parseQueryDateTime(String value, String field) {
        try {
            return LocalDateTime.parse(value, QUERY_DATE_TIME_FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    field + " must match yyyyMMddHHmm", ex);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
