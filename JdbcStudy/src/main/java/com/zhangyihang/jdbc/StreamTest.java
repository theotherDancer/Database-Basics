package com.zhangyihang.jdbc;

import java.util.ArrayList;
import java.util.List;

public class StreamTest {
    static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("小林");
        names.add("");
        names.add("   ");
        names.add("老周");
        names.add("小王");

        /*List<String> result = names.stream()
        .filter(name -> name.startsWith("小"))
        .map(name -> "学生：" + name)
        .toList();*/

        List<String> result = names.stream()
                .filter(name -> !name.isBlank())
                .filter(name -> name.startsWith("小"))
                .map(name -> "学生："+name)
                .toList();

        System.out.println(result);
        System.out.println(names);
        //////////////////////////////
        long count = names.stream()
                .filter(name -> !name.isBlank())
                .count();

        System.out.println("有效姓名数量：" + count);
    }
}
