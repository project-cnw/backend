package com.example.project_cnw.common.enums;

public enum CourseRegistrationStatus {
    CART("장바구니"),
    APPLIED("신청완료"),
    APPROVED("승인완료"),
    CANCELLED("취소");

    private final String description;

    CourseRegistrationStatus(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
