package com.example.award.service;

import com.example.award.repository.AwardRepository;
import com.example.award.service.dto.AwardApiResponse;
import com.example.award.service.dto.AwardCommand;
import com.example.award.service.dto.AwardInfo;
import com.example.award.service.dto.AwardPageableResponse;
import com.example.award.service.dto.AwardStatsResponse;
import com.example.award.service.dto.CompanyAwardStats;
import com.example.award.service.dto.InstitutionRank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AwardService {

    private static final Logger logger = LoggerFactory.getLogger(AwardService.class);

    private final RestClient restClient;
    private final AwardRepository awardRepository;
    private final DateTimeFormatter queryDateTimeFormatter;

    @Value("${award.api.service-key}")
    private String AWARD_SERVICE_KEY;
    @Value("${award.api.inqry-div}")
    private String AWARD_INQRY_DIV;
    @Value("${award.api.base-url}")
    private String AWARD_BASE_URL;

    public AwardPageableResponse getAwardResults(AwardCommand command) {

        AwardApiResponse response = requestAwardApi(command, 1, 20);

        if (response == null || response.getResponse() == null || response.getResponse().getBody() == null) {
            logger.warn("Award API response is empty.");
            return new AwardPageableResponse(
                    null,
                    null,
                    0,
                    1,
                    0,
                    List.of()
            );
        }

        if (response.getResponse().getHeader() != null) {
            logger.info(
                    "Award API response header: resultCode={}, resultMsg={}",
                    response.getResponse().getHeader().getResultCode(),
                    response.getResponse().getHeader().getResultMsg()
            );
        } else {
            logger.warn("Award API response header is missing.");
        }

        AwardApiResponse.Body body = response.getResponse().getBody();
        List<AwardInfo> itemList = body.getItems() == null
                ? List.of()
                : body.getItems();

        if (body.getTotalCount() != null && body.getTotalCount() == 0) {
            return new AwardPageableResponse(
                    "NO_RESULTS",
                    "조회 결과가 없습니다.",
                    body.getNumOfRows(),
                    body.getPageNo(),
                    body.getTotalCount(),
                    itemList
            );
        }

        return new AwardPageableResponse(
                response.getResponse().getHeader() == null ? null : response.getResponse().getHeader().getResultCode(),
                response.getResponse().getHeader() == null ? null : response.getResponse().getHeader().getResultMsg(),
                body.getNumOfRows(),
                body.getPageNo(),
                body.getTotalCount(),
                itemList
        );
    }

    public AwardPageableResponse getAwardResultsRange(AwardCommand command) {
        LocalDateTime start = LocalDateTime.parse(command.inqryBgnDt(), queryDateTimeFormatter);
        LocalDateTime end = LocalDateTime.parse(command.inqryEndDt(), queryDateTimeFormatter);

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("inqryBgnDt must be before or equal to inqryEndDt.");
        }
        if (end.isAfter(start.plusYears(5))) {
            throw new IllegalArgumentException("Date range cannot exceed 5 years.");
        }

        List<AwardInfo> aggregatedItems = new ArrayList<>();
        AwardApiResponse.Header header = null;

        LocalDateTime windowStart = start;
        while (!windowStart.isAfter(end)) {
            LocalDateTime windowEnd = windowStart.plusDays(30);
            if (windowEnd.isAfter(end)) {
                windowEnd = end;
            }

            FetchResult fetchResult = fetchAllPages(command, windowStart, windowEnd);
            aggregatedItems.addAll(fetchResult.items());

            if (header == null && fetchResult.header() != null) {
                header = fetchResult.header();
            }

            windowStart = windowEnd.plusMinutes(1);
        }

        return new AwardPageableResponse(
                aggregatedItems.isEmpty() ? "NO_RESULTS" : (header == null ? null : header.getResultCode()),
                aggregatedItems.isEmpty() ? "조회 결과가 없습니다." : (header == null ? null : header.getResultMsg()),
                aggregatedItems.size(),
                1,
                aggregatedItems.size(),
                aggregatedItems
        );
    }

    public AwardStatsResponse getAwardStatsRange(AwardCommand command) {
        AwardPageableResponse response = getAwardResultsRange(command);
        List<AwardInfo> items = response.items();
        return buildStats(response.resultCode(), response.resultMsg(), items);
    }

    public AwardStatsResponse getAwardStats(List<AwardInfo> items) {
        return buildStats("00", "정상", items);
    }

    private FetchResult fetchAllPages(AwardCommand command, LocalDateTime start, LocalDateTime end) {
        int numOfRows = 20;
        int pageNo = 1;
        int totalCount = Integer.MAX_VALUE;
        List<AwardInfo> items = new ArrayList<>();
        AwardApiResponse.Header header = null;

        while ((pageNo - 1) * numOfRows < totalCount) {
            AwardApiResponse response = requestAwardApi(withDates(command, start, end), pageNo, numOfRows);
            if (response == null || response.getResponse() == null || response.getResponse().getBody() == null) {
                logger.warn("Award API response is empty for range segment.");
                break;
            }

            if (header == null) {
                header = response.getResponse().getHeader();
            }

            AwardApiResponse.Body body = response.getResponse().getBody();
            if (body.getItems() != null) {
                items.addAll(body.getItems());
            }

            if (body.getTotalCount() != null) {
                totalCount = body.getTotalCount();
            } else if (body.getItems() == null || body.getItems().isEmpty()) {
                break;
            }

            if (body.getItems() == null || body.getItems().isEmpty()) {
                break;
            }

            pageNo += 1;
        }

        return new FetchResult(items, header);
    }

    private AwardApiResponse requestAwardApi(AwardCommand command, int pageNo, int numOfRows) {

        URI requestUri = UriComponentsBuilder.fromUriString(AWARD_BASE_URL)
                .pathSegment("getScsbidListSttusServcPPSSrch")
                .queryParam("serviceKey", AWARD_SERVICE_KEY)
                .queryParam("inqryDiv", AWARD_INQRY_DIV)
                .queryParam("numOfRows", numOfRows)
                .queryParam("page", pageNo)
                .queryParam("type", "json")
                .queryParam("inqryBgnDt", command.inqryBgnDt())
                .queryParam("inqryEndDt", command.inqryEndDt())
                .queryParam("dminsttNm", command.dminsttNm())
                .queryParam("bidNtceNm", command.bidNtceNm())
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        logger.info("Award API request URI: {}", requestUri);

        return restClient.get()
                .uri(requestUri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    private AwardCommand withDates(AwardCommand command, LocalDateTime start, LocalDateTime end) {
        String bgnDt = queryDateTimeFormatter.format(start);
        String endDt = queryDateTimeFormatter.format(end);

        return new AwardCommand(
                bgnDt,
                endDt,
                command.dminsttNm(),
                command.bidNtceNm()
        );
    }

    private record FetchResult(List<AwardInfo> items, AwardApiResponse.Header header) {
    }

    private AwardStatsResponse buildStats(String resultCode, String resultMsg, List<AwardInfo> items) {
        if (items == null || items.isEmpty()) {
            return new AwardStatsResponse(
                    "NO_RESULTS",
                    "조회 결과가 없습니다.",
                    0,
                    List.of()
            );
        }

        Map<CompanyKey, Integer> winCounts = new HashMap<>();
        Map<CompanyKey, Map<String, Integer>> institutionCounts = new HashMap<>();

        for (AwardInfo item : items) {
            CompanyKey key = CompanyKey.from(item);
            if (key == null) {
                continue;
            }

            winCounts.merge(key, 1, Integer::sum);

            String institution = item.dminsttNm();
            if (institution != null && !institution.isBlank()) {
                institutionCounts
                        .computeIfAbsent(key, unused -> new HashMap<>())
                        .merge(institution, 1, Integer::sum);
            }
        }

        List<CompanyAwardStats> companies = winCounts.entrySet().stream()
                .map(entry -> {
                    CompanyKey key = entry.getKey();
                    int winCount = entry.getValue();
                    Map<String, Integer> instMap = institutionCounts.getOrDefault(key, Map.of());
                    List<InstitutionRank> topInstitutions = instMap.entrySet().stream()
                            .sorted(
                                    Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                                            .reversed()
                                            .thenComparing(Map.Entry::getKey)
                            )
                            .limit(3)
                            .map(instEntry -> new InstitutionRank(instEntry.getKey(), instEntry.getValue()))
                            .toList();
                    return new CompanyAwardStats(key.name(), key.bizno(), winCount, topInstitutions);
                })
                .sorted(
                        Comparator.<CompanyAwardStats>comparingInt(CompanyAwardStats::winCount)
                                .reversed()
                                .thenComparing(CompanyAwardStats::bidwinnrNm, Comparator.nullsLast(String::compareTo))
                )
                .toList();

        return new AwardStatsResponse(
                resultCode,
                resultMsg,
                items.size(),
                companies
        );
    }

    private record CompanyKey(String bizno, String name) {
        static CompanyKey from(AwardInfo item) {
            if (item == null) {
                return null;
            }
            String bizno = normalize(item.bidwinnrBizno());
            String name = normalize(item.bidwinnrNm());
            if (bizno == null && name == null) {
                return null;
            }
            return new CompanyKey(bizno, name);
        }
    }

    private static String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
