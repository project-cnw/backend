package com.example.project_cnw.common.enums;

public enum LectureSemester {
    FIRST("1학기"),
    SECOND("2학기");

    private final String description;

    LectureSemester(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
