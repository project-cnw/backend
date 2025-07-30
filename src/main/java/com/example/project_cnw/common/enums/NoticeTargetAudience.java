package com.example.project_cnw.common.enums;

public enum NoticeTargetAudience {
    ALL("전체"),
    STUDENT("학생"),
    TEACHER("교사");

    private final String description;

    NoticeTargetAudience(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
