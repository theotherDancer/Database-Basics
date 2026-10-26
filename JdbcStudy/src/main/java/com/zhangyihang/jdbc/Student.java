package com.zhangyihang.jdbc;

public class Student {
    private String StudentId;
    private String name;

    public Student(String studentId, String name) {
        this.StudentId = studentId;
        this.name = name;
    }
    public String getStudentId() {
        return StudentId;
    }

    public String getName() {
        return name;
    }
}