package com.example.project_cnw.common.enums;

public enum CourseHistorySemester {
    FIRST("1학기"),
    SECOND("2학기");

    private final String description;

    CourseHistorySemester(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
