package com.example.award.service.dto;

import java.util.List;

public record AwardPageableResponse (
     String resultCode,
     String resultMsg,
     Integer numOfRows,
     Integer pageNo,
     Integer totalCount,
     List<AwardInfo> items
    ) {

}

