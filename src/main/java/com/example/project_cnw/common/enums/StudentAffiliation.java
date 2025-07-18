package com.example.project_cnw.common.enums;

public enum StudentAffiliation {
    LIBERAL_ARTS("문과"),
    NATURAL_SCIENCES("이과");

    private final String description;

    StudentAffiliation(String description){
        this.description = description;
    }
}
