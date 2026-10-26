package com.zhangyihang.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
public class ConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/campus_learning";
        String username = "root";

        // 从环境变量 DB_PASSWORD 读取密码，不写入源码
        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isEmpty()) {
            System.out.println("请先在运行配置中设置 DB_PASSWORD");
            return;
        }

        try (Connection connection =
                     DriverManager.getConnection(url, username, password)) {

            System.out.println("数据库连接成功！");
            System.out.println("当前数据库：" + connection.getCatalog());
            List<Student> students = findAll(connection);

            System.out.println("当前学生人数：" + students.size());

            for (Student student : students) {
                System.out.println(
                        student.getStudentId() + "：" + student.getName()
                );
            }


            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("请输入学号，输入 0 退出：");
                String inputId = scanner.nextLine().strip();

                if ("0".equals(inputId)) {
                    break;
                }

                if (inputId.isEmpty()) {
                    System.out.println("学号不能为空，请重新输入");
                    continue;
                }


                if ("all".equals(inputId)) {
                    List<Student> allStudents = findAll(connection);

                    System.out.println("当前学生人数：" + allStudents.size());

                    for (Student student : allStudents) {
                        System.out.println(
                                student.getStudentId() + "：" + student.getName()
                        );
                    }

                    continue;
                }




                Student student = findById(connection, inputId);

                if (student == null) {
                    System.out.println("没有找到该学生");
                } else {
                    System.out.println(
                            student.getStudentId() + "：" + student.getName()
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("连接失败：" + e.getMessage());
        }
    }
    public static Student findById(Connection connection, String studentId)
            throws SQLException {

        String sql =
                "SELECT student_id, name FROM student WHERE student_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("student_id");
                    String name = resultSet.getString("name");

                    return new Student(id, name);
                }

                return null;
            }
        }
    }

    public static List<Student> findAll(Connection connection)
            throws SQLException {

        List<Student> students = new ArrayList<>();

        String sql =
                "SELECT student_id, name FROM student ORDER BY student_id";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("student_id");
                String name = resultSet.getString("name");

                Student student = new Student(id, name);
                students.add(student);
            }
        }

        return students;
    }





}