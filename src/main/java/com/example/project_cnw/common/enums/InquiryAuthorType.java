package com.example.project_cnw.common.enums;

public enum InquiryAuthorType {
    STUDENT("학생"),
    TEACHER("교사");

    private final String description;

    InquiryAuthorType(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
