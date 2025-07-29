package com.example.project_cnw.common.enums;

public enum SubjectAffiliation {
    LIBERAL_ARTS("문과"),
    NATURAL_SCIENCES("이과"),
    COMMON("공통");

    private final String description;

    SubjectAffiliation(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
