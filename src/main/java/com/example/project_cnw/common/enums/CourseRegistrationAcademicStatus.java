package com.example.project_cnw.common.enums;

public enum CourseRegistrationAcademicStatus {
    ENROLLED("수강중"),
    COMPLETED("이수완료"),
    NOT_ENROLLED("미수강");

    private final String description;

    CourseRegistrationAcademicStatus(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
