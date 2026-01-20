package com.example.award.controller.dto;

import java.util.List;

public class AwardResponse {
    private String resultCode;
    private String resultMsg;
    private Integer numOfRows;
    private Integer pageNo;
    private Integer totalCount;
    private List<AwardResultItem> items;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Integer getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<AwardResultItem> getItems() {
        return items;
    }

    public void setItems(List<AwardResultItem> items) {
        this.items = items;
    }
}
