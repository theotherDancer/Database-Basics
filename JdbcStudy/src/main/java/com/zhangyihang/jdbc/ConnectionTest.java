package com.zhangyihang.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;


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

          /*  int rows = StudentDao.insert(connection, "2026006", "小赵", 20);
            System.out.println("新增行数：" + rows);    */
            List<Student> students = StudentDao.findAll(connection);

            System.out.println("当前学生人数：" + students.size());

          /*  int rows = StudentDao.updateAge(connection, "not-exist-test", 21);
            System.out.println("修改返回行数：" + rows);*/


            for (Student student : students) {
                System.out.println(
                        student.getStudentId() + "：" + student.getName()
                );
            }


            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n===== 学生管理 =====");
                System.out.println("1. 查询全部学生");
                System.out.println("2. 按学号查询");
                System.out.println("3. 新增学生");
                System.out.println("4. 修改年龄");
                System.out.println("5. 删除学生");
                System.out.println("0. 退出");
                System.out.print("请选择：");

                String choice = scanner.nextLine().strip();

                if ("0".equals(choice)) {
                    break;
                }

                if ("1".equals(choice)) {
                    List<Student> allStudents = StudentDao.findAll(connection);

                    System.out.println("当前学生人数：" + allStudents.size());

                    for (Student student : allStudents) {
                        System.out.println(
                                student.getStudentId() + "：" + student.getName()
                        );
                    }
                }
                else if ("2".equals(choice)) {
                    System.out.print("请输入学号：");
                    String studentId = scanner.nextLine().strip();

                    if (studentId.isEmpty()) {
                        System.out.println("学号不能为空");
                        continue;
                    }

                    Student student = StudentDao.findById(connection, studentId);

                    if (student == null) {
                        System.out.println("没有找到该学生");
                    } else {
                        System.out.println(
                                student.getStudentId() + "：" + student.getName()
                        );
                    }
                }
                else if ("3".equals(choice)) {
                    System.out.print("请输入学号：");
                    String studentId = scanner.nextLine().strip();

                    System.out.print("请输入姓名：");
                    String name = scanner.nextLine().strip();

                    if (studentId.isEmpty() || name.isEmpty()) {
                        System.out.println("学号和姓名不能为空");
                        continue;
                    }

                    System.out.print("请输入年龄：");
                    int age;

                    try {
                        age = Integer.parseInt(scanner.nextLine().strip());
                    } catch (NumberFormatException e) {
                        System.out.println("年龄请输入整数");
                        continue;
                    }

                    if (age < 0 || age > 150) {
                        System.out.println("年龄应在 0～150 之间");
                        continue;
                    }

                    try {
                        int rows = StudentDao.insert(connection, studentId, name, age);
                        System.out.println("新增行数：" + rows);
                    } catch (SQLException e) {
                        System.out.println("新增失败：" + e.getMessage());
                    }
                }
                else if ("4".equals(choice)) {
                    System.out.print("请输入要修改的学号：");
                    String studentId = scanner.nextLine().strip();

                    if (studentId.isEmpty()) {
                        System.out.println("学号不能为空");
                        continue;
                    }

                    System.out.print("请输入新年龄：");
                    int age;

                    try {
                        age = Integer.parseInt(scanner.nextLine().strip());
                    } catch (NumberFormatException e) {
                        System.out.println("年龄请输入整数");
                        continue;
                    }

                    if (age < 0 || age > 150) {
                        System.out.println("年龄应在 0～150 之间");
                        continue;
                    }

                    try {
                        int rows = StudentDao.updateAge(connection, studentId, age);

                        if (rows == 0) {
                            System.out.println("没有找到该学生");
                        } else {
                            System.out.println("修改成功");
                        }
                    } catch (SQLException e) {
                        System.out.println("修改失败：" + e.getMessage());
                    }
                }
                else if ("5".equals(choice)) {
                    System.out.print("请输入要删除的学号：");
                    String studentId = scanner.nextLine().strip();

                    if (studentId.isEmpty()) {
                        System.out.println("学号不能为空");
                        continue;
                    }

                    System.out.print("确认删除该学生？输入 y 确认：");
                    String confirm = scanner.nextLine().strip();

                    if (!"y".equals(confirm)) {
                        System.out.println("已取消删除");
                        continue;
                    }

                    try {
                        int rows = StudentDao.deleteById(connection, studentId);

                        if (rows == 0) {
                            System.out.println("没有找到该学生");
                        } else {
                            System.out.println("删除成功");
                        }
                    } catch (SQLException e) {
                        System.out.println("删除失败：" + e.getMessage());
                    }
                }
                else {
                    System.out.println("选项无效，请重新输入");
                }
            }

        } catch (SQLException e) {
            System.out.println("数据库操作失败：" + e.getMessage());
        }
    }







}
