package com.zhangyihang.jdbc;

import java.util.ArrayList;
import java.util.List;

public class StreamDistinctTest {
    static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("小林");
        names.add("小周");
        names.add("小林");
        names.add("小王");
        names.add("小周");

        List<String> result = names.stream()
                .distinct()
                .toList();

        long uniqueCount = names.stream()
                .distinct()
                .count();

        System.out.println("原名单：" + names);
        System.out.println("去重后：" + result);
        System.out.println("不同姓名数量：" + uniqueCount);
    }
}
