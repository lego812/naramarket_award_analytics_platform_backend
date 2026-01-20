package com.example.award.service.dto;

public record AwardStoreResponse (
    String resultCode,
    String resultMsg,
    Integer savedCount,
    Integer totalCount,
    Integer pageNo
    ){
}
