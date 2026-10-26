-- 只读查询，可按段运行；结果取决于现有数据，不承诺固定行数。
USE campus_learning;

-- 三表连接：谁选了什么课。
SELECT s.name, c.course_name
FROM student_course AS sc
JOIN student AS s ON sc.student_id = s.student_id
JOIN course AS c ON sc.course_id = c.course_id
ORDER BY sc.student_id, sc.course_id;

-- 多行子查询：选了 Java 的学生。
SELECT student_id, name
FROM student
WHERE student_id IN (
    SELECT student_id FROM student_course WHERE course_id = 101
);

-- 单值子查询：高于平均年龄的学生，AVG 忽略 NULL。
SELECT student_id, name, age
FROM student
WHERE age > (SELECT AVG(age) FROM student);

-- 相关子查询：没有选课的学生。
SELECT s.student_id, s.name
FROM student AS s
WHERE NOT EXISTS (
    SELECT 1 FROM student_course AS sc WHERE sc.student_id = s.student_id
);

-- 班次报名详情。
SELECT s.name, d.departure_id, d.departure_time, d.origin, d.destination
FROM departure_registration AS r
JOIN student AS s ON r.student_id = s.student_id
JOIN departure AS d ON r.departure_id = d.departure_id
ORDER BY d.departure_time, s.student_id;

-- 每个班次的报名人数，保留零报名班次。
SELECT d.departure_id, d.departure_time,
       COUNT(r.student_id) AS registration_count
FROM departure AS d
LEFT JOIN departure_registration AS r ON d.departure_id = r.departure_id
GROUP BY d.departure_id, d.departure_time
ORDER BY d.departure_id;
-- 要筛选至少两人，在上面的 ORDER BY 前加入：
-- HAVING COUNT(r.student_id) >= 2

-- 无人报名的班次。
SELECT d.departure_id, d.departure_time, d.origin, d.destination
FROM departure AS d
WHERE NOT EXISTS (
    SELECT 1 FROM departure_registration AS r
    WHERE r.departure_id = d.departure_id
);

-- 收尾练习的参考答案：1 号班次报名学生。
SELECT s.student_id, s.name
FROM departure_registration AS r
JOIN student AS s ON r.student_id = s.student_id
WHERE r.departure_id = 1
ORDER BY s.student_id ASC;
