package com.zhangyihang.jdbc;

import java.util.ArrayList;
import java.util.List;

public class GenericMethodTest {
    static void main(String[] args) {


        // 一个包含 "小林" 的 List<String>。
         List<String> name = new ArrayList<>();
         name.add("小林");
       // 一个包含 10 的 List<Integer>。
        List<Integer> num = new ArrayList<>();
         num.add(10);
       // 一个空的 List<Integer>。*/
        List<Integer> empty = new ArrayList<>();
        String firstName = first(name);
        Integer firstNum = first(num);
        Integer firstEmpty = first(empty);

        System.out.println(firstName);
        System.out.println(firstNum);
        System.out.println(firstEmpty);

    }

    public static <T> T first(List<T> list) {
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
}
