package com.example.project_cnw.common.enums;

public enum InquiryStatus {
    NEW("신규"),
    IN_PROGRESS("처리중"),
    CLOSED("완료");

    private final String description;

    InquiryStatus(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
