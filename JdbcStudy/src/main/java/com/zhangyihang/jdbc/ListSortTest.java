package com.zhangyihang.jdbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListSortTest {
    static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(20);
        numbers.add(10);
        numbers.add(30);

        Collections.reverse(numbers);
        System.out.println("反转后：" + numbers);

        Collections.sort(numbers);
        System.out.println("升序后：" + numbers);

        Collections.sort(numbers, Collections.reverseOrder());
        System.out.println("降序后：" + numbers);


        List<Student> students = new ArrayList<>();
        students.add(new Student("2026003", "小林"));
        students.add(new Student("2026001", "小林"));
        students.add(new Student("2026002", "小林"));

        students.sort(Comparator.comparing(Student::getStudentId));

        students.sort(Comparator.comparing(Student::getStudentId));

        for (Student student : students) {
            System.out.println(student.getStudentId() + "：" + student.getName());
        }
    }
}
