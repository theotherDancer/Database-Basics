package com.zhangyihang.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
public class LambdaTest {
    static void main(String[] args) {
        // 一个包含 "小林" 的 List<String>。
        List<String> names = new ArrayList<>();
        names.add("小林");
        names.add("");
        names.add("   ");
        names.add("小周");
        System.out.println(names);
        names.removeIf(name -> name.isBlank());
        System.out.println(names);

    }
}
