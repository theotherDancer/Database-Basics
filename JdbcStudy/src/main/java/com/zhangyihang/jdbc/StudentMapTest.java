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
}
