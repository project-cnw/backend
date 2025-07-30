package com.example.project_cnw.common.enums;

public enum CourseRegistrationSubjectApprovalStatus {
    PENDING("승인대기"),
    APPROVED("승인완료"),
    REJECTED("승인거부");

    private final String description;

    CourseRegistrationSubjectApprovalStatus(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
