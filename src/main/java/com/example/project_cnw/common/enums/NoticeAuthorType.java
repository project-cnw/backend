package com.example.project_cnw.common.enums;

public enum NoticeAuthorType {
    ADMIN("관리자"),
    TEACHER("교사");

    private final String description;

    NoticeAuthorType(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
