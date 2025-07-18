package com.example.project_cnw.common.enums;

public enum StudentStatus {
    PENDING("승인대기"),
    APPROVED("승인허락"),
    REJECTED("승인거절"),
    GRADUATED("졸업");

    private final String description;

    StudentStatus(String description) {
        this.description = description;
    }
}
