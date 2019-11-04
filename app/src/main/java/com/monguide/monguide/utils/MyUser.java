package com.monguide.monguide.utils;

public class MyUser {
    private String name;
    private EducationDetails educationDetails;

    public MyUser() {}

    public MyUser(String name, EducationDetails educationDetails) {
        this.name = name;
        this.educationDetails = educationDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EducationDetails getEducationDetails() {
        return educationDetails;
    }

    public void setEducationDetails(EducationDetails educationDetails) {
        this.educationDetails = educationDetails;
    }
}
