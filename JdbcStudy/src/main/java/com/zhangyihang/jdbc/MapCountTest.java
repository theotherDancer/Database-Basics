package com.zhangyihang.jdbc;

import java.util.HashMap;
import java.util.Map;

public class MapCountTest {
    static void main(String[] args) {
        String[] studentIds = {
                "2026001", "2026002", "2026001",
                "2026003", "2026002", "2026001"
        };

        Map<String, Integer> counts = new HashMap<>();

        for (String studentId : studentIds) {
            int oldCount = counts.getOrDefault(studentId, 0);
            counts.put(studentId, oldCount + 1);
        }
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            System.out.println(
                    entry.getKey() + " 出现了 " + entry.getValue() + " 次"
            );
        }
    }
}
