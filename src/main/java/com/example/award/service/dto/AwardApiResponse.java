package com.example.award.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class AwardApiResponse {
    private Response response;

    @Data
    public static class Response {
        private Header header;
        private Body body;
    }

    @Data
    public static class Header {
        private String resultMsg;
        private String resultCode;
    }

    @Data
    public static class Body {
        private List<AwardInfo> items;
        private Integer totalCount;
        private Integer numOfRows;
        private Integer pageNo;
    }
}
