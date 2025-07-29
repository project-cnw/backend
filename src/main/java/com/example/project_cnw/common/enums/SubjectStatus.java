package com.example.project_cnw.common.enums;

public enum SubjectStatus {
    APPROVED("승인됨"),
    PENDING("승인 대기중"),
    REJECTED("거절됨");

    private final String description;

    SubjectStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
