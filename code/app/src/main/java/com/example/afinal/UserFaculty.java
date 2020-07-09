package com.example.afinal;

public class UserFaculty {
    private String facultyId;
    private String facultyName;
    private String facultyEmail;
    UserFaculty()
    {

    }

    public UserFaculty(String facultyId, String facultyName, String facultyEmail) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyEmail = facultyEmail;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getFacultyEmail() {
        return facultyEmail;
    }
}
