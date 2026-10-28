package com.zhangyihang.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentMapTest {
    public static Map<String, Student> indexById(List<Student> list) {
        Map<String, Student> students = new HashMap<>();

        // 1. 遍历 list
        // 2. 学号作为键，学生对象作为值，放入 students
        //
        for (Student student : list) {
            students.put(student.getStudentId(), student);
        }
        return students;
    }

    public static void main(String[] args) {
        List<Student> list = new java.util.ArrayList<>();
        list.add(new Student("2026001", "小林"));
        list.add(new Student("2026002", "小周"));
        list.add(new Student("2026001", "小林（新）"));

        Map<String, Student> students = indexById(list);

        System.out.println(list.size());
        System.out.println(students.size());
        System.out.println(students.get("2026001").getName());
    }


}
