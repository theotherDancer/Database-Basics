package com.zhangyihang.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateStudentTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/campus_learning";
        String username = "root";
        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isEmpty()) {
            System.out.println("请先设置 DB_PASSWORD");
            return;
        }

        String sql = "UPDATE student SET age = ? WHERE student_id = ?";

        try (Connection connection =
                     DriverManager.getConnection(url, username, password);
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, 19);
            statement.setString(2, "2026005");
            int rows = statement.executeUpdate();
            System.out.println("影响行数：" + rows);
        } catch (SQLException e) {
            System.out.println("新增失败：" + e.getMessage());
        }
    }
}

