package com.monguide.monguide.utils;

public class EducationDetails {
    private String collegeName;
    private String courseName;
    private int graduationYear;

    public EducationDetails() {}

    public EducationDetails(String collegeName, String courseName, int graduationYear) {
        this.collegeName = collegeName;
        this.courseName = courseName;
        this.graduationYear = graduationYear;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }
}
