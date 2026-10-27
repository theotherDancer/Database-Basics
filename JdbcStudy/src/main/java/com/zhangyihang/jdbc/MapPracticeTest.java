package com.zhangyihang.jdbc;

import java.util.HashMap;
import java.util.Map;

public class MapPracticeTest {
    public static void main(String[] args) {
        Map<String, String> names = new HashMap<>();
        // 在下面完成任务


         //添加 "2026001" → "小林"、"2026002" → "小周"。
            names.put("2026001","小林");
            names.put("2026002","小周");
        //将 "2026001" 对应的姓名改为 "小林（新）"，并打印 put() 返回的旧姓名。
            String put = names.put("2026001", "小林（新）");
            System.out.println(put);
        //删除 "2026002"，打印 remove() 返回的姓名。
            String remove = names.remove("2026002");
             System.out.println(remove);
        //用entrySet() 和增强 for 遍历剩余内容。
            for (Map.Entry<String, String> entry : names.entrySet()) {
            System.out.println(entry.getKey() + "：" + entry.getValue());
        }


    }
}
