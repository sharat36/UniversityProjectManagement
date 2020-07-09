package com.example.afinal;

public class UserStudent {
    private String studentId;
    private String studentName;
    private String studentEmail;
    UserStudent()
    {

    }
    public UserStudent(String studentId, String studentName, String studentEmail) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }
}
