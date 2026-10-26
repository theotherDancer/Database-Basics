package com.zhangyihang.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class TransactionTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/campus_learning";
        String username = "root";
        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isEmpty()) {
            System.out.println("请先设置 DB_PASSWORD");
            return;
        }

        try (Connection connection =
                     DriverManager.getConnection(url, username, password)) {

            connection.setAutoCommit(false);

            try {
                // 下一步在这里添加两次修改
                String sql = "UPDATE student SET age = ? WHERE student_id = ?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, 30);
                    statement.setString(2, "2026001");

                    int rows = statement.executeUpdate();

                    if (rows != 1) {
                        throw new SQLException("未匹配到预期的一名学生");
                    }

                    System.out.println("第一步执行成功，尚未提交");
                }

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, 40);
                    statement.setString(2, "2026002");

                    int rows = statement.executeUpdate();

                    if (rows != 1) {
                        throw new SQLException("第二步失败：没有找到目标学生");
                    }
                }

                connection.commit();
                System.out.println("事务提交成功");

            } catch (SQLException e) {
                connection.rollback();
                System.out.println("事务已回滚：" + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("数据库操作失败：" + e.getMessage());
        }
    }
}