package com.example.project_cnw.common.enums;

public enum SubjectType {
    REQUIRED("필수"),
    ELECTIVE("선택");

    private final String description;

    SubjectType(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
