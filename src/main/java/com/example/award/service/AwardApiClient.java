package com.example.award.service;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.award.controller.dto.AwardRequest;
import com.example.award.controller.dto.AwardResponse;
import com.example.award.controller.dto.AwardResultItem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AwardApiClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String baseUrl;
    private final String serviceKey;
    private final String defaultInqryDiv;

    public AwardApiClient(
            RestTemplateBuilder restTemplateBuilder,
            ObjectMapper objectMapper,
            @Value("${award.api.base-url}") String baseUrl,
            @Value("${award.api.service-key}") String serviceKey,
            @Value("${award.api.inqry-div}") String defaultInqryDiv) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
        this.baseUrl = baseUrl;
        this.serviceKey = serviceKey;
        this.defaultInqryDiv = defaultInqryDiv;
    }

    public AwardResponse fetchAwardResults(AwardRequest request) {
        URI uri = buildUri(request);
        String responseBody = restTemplate.getForObject(uri, String.class);
        if (responseBody == null || responseBody.isBlank()) {
            AwardResponse empty = new AwardResponse();
            empty.setItems(Collections.emptyList());
            return empty;
        }

        try {
            JsonNode root = objectMapper.readTree(responseBody);
            if (root.has("response")) {
                return mapWrappedResponse(root);
            }
            return objectMapper.readValue(responseBody, AwardResponse.class);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to parse award API response", ex);
        }
    }

    private URI buildUri(AwardRequest request) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("ServiceKey", pick(request.getServiceKey(), serviceKey))
                .queryParam("type", pick(request.getType(), "json"))
                .queryParam("inqryDiv", pick(request.getInqryDiv(), defaultInqryDiv))
                .queryParam("numOfRows", request.getNumOfRows())
                .queryParam("pageNo", request.getPageNo());

        addIfPresent(builder, "inqryBgnDt", request.getInqryBgnDt());
        addIfPresent(builder, "inqryEndDt", request.getInqryEndDt());
        addIfPresent(builder, "bidNtceNo", request.getBidNtceNo());
        addIfPresent(builder, "bidNtceNm", request.getBidNtceNm());
        addIfPresent(builder, "ntceInsttCd", request.getNtceInsttCd());
        addIfPresent(builder, "ntceInsttNm", request.getNtceInsttNm());
        addIfPresent(builder, "dminsttCd", request.getDminsttCd());
        addIfPresent(builder, "dminsttNm", request.getDminsttNm());
        addIfPresent(builder, "refNo", request.getRefNo());
        addIfPresent(builder, "prtcptLmtRgnCd", request.getPrtcptLmtRgnCd());
        addIfPresent(builder, "prtcptLmtRgnNm", request.getPrtcptLmtRgnNm());
        addIfPresent(builder, "indstrytyCd", request.getIndstrytyCd());
        addIfPresent(builder, "indstrytyNm", request.getIndstrytyNm());
        addIfPresent(builder, "presmptPrceBgn", request.getPresmptPrceBgn());
        addIfPresent(builder, "presmptPrceEnd", request.getPresmptPrceEnd());
        addIfPresent(builder, "dtilPrdctClsfcNo", request.getDtilPrdctClsfcNo());
        addIfPresent(builder, "masYn", request.getMasYn());
        addIfPresent(builder, "prcrmntReqNo", request.getPrcrmntReqNo());
        addIfPresent(builder, "intrntnlDivCd", request.getIntrntnlDivCd());
        addIfPresent(builder, "bizno", request.getBizno());

        return builder.build(true).toUri();
    }

    private void addIfPresent(UriComponentsBuilder builder, String name, String value) {
        if (value != null && !value.isBlank()) {
            builder.queryParam(name, value);
        }
    }

    private String pick(String value, String fallback) {
        return (value == null || value.isBlank()) ? fallback : value;
    }

    private AwardResponse mapWrappedResponse(JsonNode root) {
        AwardResponse response = new AwardResponse();
        JsonNode header = root.path("response").path("header");
        JsonNode body = root.path("response").path("body");

        response.setResultCode(asTextOrNull(header, "resultCode"));
        response.setResultMsg(asTextOrNull(header, "resultMsg"));
        response.setNumOfRows(asIntOrNull(body, "numOfRows"));
        response.setPageNo(asIntOrNull(body, "pageNo"));
        response.setTotalCount(asIntOrNull(body, "totalCount"));
        response.setItems(readItems(body.path("items")));
        return response;
    }

    private List<AwardResultItem> readItems(JsonNode itemsNode) {
        if (itemsNode.isMissingNode() || itemsNode.isNull()) {
            return Collections.emptyList();
        }
        JsonNode itemNode = itemsNode.has("item") ? itemsNode.get("item") : itemsNode;
        List<AwardResultItem> items = new ArrayList<>();
        if (itemNode.isArray()) {
            for (JsonNode node : itemNode) {
                items.add(objectMapper.convertValue(node, AwardResultItem.class));
            }
        } else if (itemNode.isObject()) {
            items.add(objectMapper.convertValue(itemNode, AwardResultItem.class));
        }
        return items;
    }

    private String asTextOrNull(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value == null || value.isNull() ? null : value.asText();
    }

    private Integer asIntOrNull(JsonNode node, String field) {
        JsonNode value = node.get(field);
        return value == null || value.isNull() ? null : value.asInt();
    }
}
