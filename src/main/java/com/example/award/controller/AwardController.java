package com.example.award.controller;

import com.example.award.controller.dto.AwardRequest;
import com.example.award.service.AwardService;
import com.example.award.service.dto.AwardInfo;
import com.example.award.service.dto.AwardPageableResponse;
import com.example.award.service.dto.AwardStatsResponse;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/award")
@RequiredArgsConstructor
public class AwardController {

    private final AwardService awardService;

    // 조달청api 호출 후 다시 반환(한달 단위로만 검색가능)
    // apiKey 탈취방지 위해 postMapping으로
    @GetMapping("/results")
    public AwardPageableResponse getAwardResults(@Valid @ModelAttribute AwardRequest request) {
        return awardService.getAwardResults(request.toCommand());
    }

    @GetMapping("/results/range")
    public AwardPageableResponse getAwardResultsRange(@Valid @ModelAttribute AwardRequest request) {
        return awardService.getAwardResultsRange(request.toCommand());
    }

    @PostMapping("/results/range/stats")
    public AwardStatsResponse getAwardResultsRangeStats(@RequestBody List<AwardInfo> items) {
        return awardService.getAwardStats(items);
    }

    // List<낙찰정보> 가지고 업체 기준으로 낙찰횟수와, 수요기관 중 해당 업체에 가장 많이 낙찰받은 횟수를 계산

    // 조달청api 호출 후 저장(한달 단위로만 검색가능)
//    @PostMapping("/results/store")
//    public AwardStoreResponse storeAwardResults(@ModelAttribute AwardRequest request) {
//    }

}
