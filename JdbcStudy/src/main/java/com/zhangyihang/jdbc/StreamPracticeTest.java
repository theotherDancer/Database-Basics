package com.zhangyihang.jdbc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StreamPracticeTest {
    static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(30);
        numbers.add(10);
        numbers.add(30);
        numbers.add(-5);
        numbers.add(20);
        numbers.add(40);
        //需求：排除负数、去重、降序排列，最后取最大的三个不同整数。
        List<Integer> result = numbers.stream()
                .filter(number -> number>=0)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .toList();

        System.out.println(result);
        System.out.println(numbers);
    }
}
