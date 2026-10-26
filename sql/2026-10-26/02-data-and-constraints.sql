-- 分段执行；普通 INSERT 重复运行会触发主键/唯一约束。
-- 前提：01-schema 对应的表已存在；新环境需先准备以下示例学生。
USE campus_learning;
-- 仅在学号尚不存在时执行，已有学生不要重复初始化：
-- INSERT INTO student (student_id, name, age) VALUES
-- ('2026001', '小林', 20), ('2026002', '小周', 21),
-- ('2026003', '小王', NULL), ('2026004', '小李', 20);

UPDATE student SET birthday = '2006-05-18' WHERE student_id = '2026001';

INSERT INTO account_demo (id, phone) VALUES (1, '13800138000'), (2, NULL);
INSERT INTO account_demo (id, phone) VALUES (4, NULL);
SELECT * FROM account_demo ORDER BY id;

-- 故意失败的练习，取消注释后一次只执行一条：
-- 手机号重复，预期 Duplicate entry：
-- INSERT INTO account_demo (id, phone) VALUES (3, '13800138000');
-- 显式 NULL 不会使用默认值，预期 status cannot be null：
-- INSERT INTO account_demo (id, phone, status) VALUES (5, '13900139000', NULL);

INSERT INTO course (course_id, course_name) VALUES (101, 'Java'), (102, '数据库');
INSERT INTO student_course (student_id, course_id) VALUES
('2026001', 101), ('2026001', 102), ('2026002', 101);
-- 以下整组重复，预期违反联合主键：
-- INSERT INTO student_course (student_id, course_id) VALUES ('2026001', 101);

INSERT INTO departure (departure_time, origin, destination)
VALUES ('2026-10-27 14:00:00', '南湖校区', '马房山校区');
-- 在同一连接中，紧接上面成功插入的单条班次保存其编号；
-- 这是整理版为避免写死编号而补充的写法，不计为已独立掌握的新知识。
SET @practice_departure_id = LAST_INSERT_ID();
SELECT * FROM departure ORDER BY departure_id;

-- 原练习实际返回编号 1；整理版使用刚取得的实际编号。
INSERT INTO departure_registration (student_id, departure_id) VALUES
('2026001', @practice_departure_id), ('2026002', @practice_departure_id);

-- 取消报名：单独选中执行，同时限制学生与班次。
-- DELETE FROM departure_registration
-- WHERE student_id = '2026001' AND departure_id = @practice_departure_id;
