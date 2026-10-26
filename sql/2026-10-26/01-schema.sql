-- 2026-10-26 学习内容整理版，不是原始 Workbench 草稿，也不是数据库备份。
-- 以下是结构参考；已有表请勿整体重跑。ALTER 仅用于已经完成前一天建表的环境。
-- 不包含 DROP、清库或自动覆盖现有数据的操作。
CREATE DATABASE IF NOT EXISTS campus_learning DEFAULT CHARACTER SET utf8mb4;
USE campus_learning;

-- 新环境先创建学生表（与当前学习后的字段一致）。
CREATE TABLE student (
    student_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    birthday DATE
);

-- 已有旧 student 表时，按需单独执行以下两条，不与上面的建表同时运行：
-- ALTER TABLE student ADD COLUMN birthday DATE;
-- ALTER TABLE student MODIFY COLUMN name VARCHAR(100) NOT NULL;

CREATE TABLE account_demo (
    id INT PRIMARY KEY,
    phone VARCHAR(20) UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT '正常'
);

CREATE TABLE course (
    course_id INT PRIMARY KEY,
    course_name VARCHAR(50) NOT NULL
);
CREATE TABLE student_course (
    student_id VARCHAR(20) NOT NULL,
    course_id INT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id)
);

CREATE TABLE departure (
    departure_id INT PRIMARY KEY AUTO_INCREMENT,
    departure_time DATETIME NOT NULL,
    origin VARCHAR(50) NOT NULL,
    destination VARCHAR(50) NOT NULL
);
CREATE TABLE departure_registration (
    student_id VARCHAR(20) NOT NULL,
    departure_id INT NOT NULL,
    PRIMARY KEY (student_id, departure_id),
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    FOREIGN KEY (departure_id) REFERENCES departure(departure_id)
);
