package com.example.project_cnw.common.enums;

public enum StudentStatus {
    PENDING("승인대기"),
    APPROVED("승인됨"),
    REJECTED("거절됨"),
    GRADUATED("졸업");

    private final String description;

    StudentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
