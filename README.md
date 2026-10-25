# 数据库学习记录

记录 MySQL、SQL 与 Java JDBC 的学习过程、练习、常见错误及修正经验，后续结合校园出行项目继续巩固。

> 学习状态：完成 SQL 核心入门，并开始 JDBC 连接与参数化查询练习；复杂查询和 Java 数据访问仍需指导，尚未达到独立熟练使用的程度。
> 本仓库保存学习笔记与 SQL 文本，不上传数据库物理文件、账号密码、连接密钥或真实用户数据。

## 学习进度

| 内容 | 状态 | 说明 |
|---|---|---|
| MySQL 环境与 Workbench | 已实践 | 连接数据库、查询版本、查看表、选择执行范围 |
| 建库建表与基本类型 | 已入门 | CREATE DATABASE、USE、VARCHAR、INT、DESCRIBE |
| 增删改查 | 已练习 | INSERT、SELECT、UPDATE、DELETE |
| 条件与分页 | 已练习 | WHERE、AND/OR、LIKE、IS NULL、ORDER BY、LIMIT/OFFSET |
| 分组统计 | 已练习 | COUNT、AVG、GROUP BY、HAVING |
| 主键、外键、一对多 | 已入门 | student 与 trip 的关系、自增编号 |
| 多表关联 | 已练习，需巩固 | INNER JOIN、LEFT JOIN、ON 与 WHERE 的区别 |
| 事务 | 已讲解并进行实验 | START TRANSACTION、COMMIT、ROLLBACK；确认提交后回滚无效 |
| 索引 | 已入门 | 普通索引与唯一索引、主键索引的区别 |
| 独立综合查询 | 进行中 | 连接、分组、计数、排序、LIMIT 的组合仍有错误 |
| 类型、约束与表结构补充 | 待学习 | 日期时间、DECIMAL、UNIQUE、CHECK、ALTER TABLE |
| 子查询与 EXISTS | 待学习 | 根据业务需要补充 |
| Maven 与 MySQL 驱动 | 已配置 | Maven 项目与 Connector/J 9.7.0 依赖加载成功 |
| JDBC 连接与查询 | 已跟做并反馈成功 | 环境变量、连接、参数化查询、结果遍历与未找到分支；仍需巩固 |
| JDBC 新增 | 已讲解，待验证 | 已提供 InsertStudentTest 示例，尚未确认实际插入结果 |
| JDBC 修改、删除与事务 | 待实操 | 结果映射、Java 事务处理等仍待进一步学习 |
| 项目表设计与查询优化 | 待学习 | 按需求设计、索引与 EXPLAIN、备份恢复 |

## 知识点索引

| 知识点 | 记录位置 |
|---|---|
| 环境、数据存放位置 | [环境与工具](#环境与工具) |
| 建库建表、主外键与自增 | [表与字段](#表与字段) |
| INSERT、SELECT、UPDATE、DELETE | [增删改查](#增删改查) |
| 条件、逗号、排序与分页 | [筛选与语法](#筛选与语法) |
| NULL、COUNT、GROUP BY、HAVING | [空值与统计](#空值与统计) |
| INNER JOIN、LEFT JOIN、零次统计 | [关联查询](#关联查询) |
| 提交、回滚及范围 | [事务](#事务) |
| 普通索引与唯一性 | [索引](#索引) |
| 问题与纠正经验 | [2026-10-25 踩坑记录](#2026-10-25-踩坑记录) |
| Maven、驱动、连接、参数与结果集 | [JDBC 晚间学习](#jdbc-evening-2026-10-25) |
| JDBC 实际错误、运行反馈与下次起点 | [晚间踩坑与修正](#晚间踩坑与修正) · [阶段结论](#本次阶段结论与下次起点) |

## 每日学习记录

### 2026-10-25：SQL 核心入门与 JDBC 初步实践

#### 环境与工具

- 实测 MySQL Server 版本为 8.0.46，Workbench 为 8.0.47。
- 学会使用 Workbench 输入 SQL、选择语句执行、查看 Result Grid 和 Action Output。
- 程序、数据库数据、SQL 文本文件是不同的内容：保存 `.sql` 文件不等于备份数据库。
- 当前数据库数据目录已经迁移至 D 盘，服务启动、3306 端口与复制文件已检查，用户通过 `SELECT @@datadir;` 确认迁移成功。
- 迁移时保留了原数据与配置备份，没有通过删除原数据释放 C 盘空间。迁移属于本次环境操作，不代表已独立掌握数据库迁移。
- `USE campus_learning;` 选择当前连接的默认数据库，不必每条语句重复执行。重新连接后应确认默认数据库。
- `SELECT VERSION();` 查看版本；`SELECT DATABASE();` 查看当前数据库；`SELECT @@datadir;` 查看运行中的数据目录。
- 展开 Schemas → campus_learning → Tables 查看表；`DESCRIBE` 查看结构，`SELECT` 查看记录。

#### 表与字段

练习数据库：`campus_learning`。以下是已经学习的表结构，不要在已有表的环境中直接重复执行建表语句。

```sql
CREATE TABLE student (
    student_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    age INT
);

CREATE TABLE trip (
    trip_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id VARCHAR(20) NOT NULL,
    departure VARCHAR(50) NOT NULL,
    destination VARCHAR(50) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student(student_id)
);
```

- 一行是一条记录，一列是一种属性。学号属于身份编号，用字符串能保留前导零。
- VARCHAR(n) 中的 n 是字符数上限，不要求每次填满。
- 主键不能重复，也不能为 NULL；普通姓名字段允许同名。
- NOT NULL 不等于禁止空字符串，`''` 与 NULL 不同。
- INT 不会自动约束年龄在合理区间，需要额外校验或约束。
- 一个学生对应多条行程，是一对多关系。trip.student_id 可以重复，trip_id 不能重复。
- 外键限制行程中的学号必须存在于学生表；关联查询仍需明确写 ON 条件。
- 自增编号由数据库生成，不保证永远连续。

#### 增删改查

```sql
-- 添加：重复执行相同学号可能触发主键冲突
INSERT INTO student (student_id, name, age)
VALUES ('2026001', '小林', 20);

-- 查询
SELECT student_id, name FROM student WHERE age < 20;

-- 修改
UPDATE student SET age = 21 WHERE student_id = '2026002';

-- 删除整条记录，表本身仍然存在
DELETE FROM student WHERE student_id = '2026099';
```

- SET 中的 `=` 表示设置值；WHERE 中的 `=` 判断是否相等。
- 修改、删除前用相同条件 SELECT 确认目标；没有 WHERE 的修改或删除可能影响全表。
- 查询结果不会自动随数据库内容更新，查看当前状态需要重新执行 SELECT。

#### 筛选与语法

- SELECT 决定显示哪些列，WHERE 决定保留哪些记录；用于筛选的列不一定出现在结果中。
- AND 要求两个条件都满足，OR 要求至少一个满足。
- `LIKE '周%'` 表示以周开头；`LIKE '%周%'` 才表示包含周。
- `%` 匹配任意数量字符（包括零个），`_` 匹配恰好一个字符。
- 同一部分列举项目用逗号，例如 SELECT 多列、SET 多项、ORDER BY 多字段；WHERE 条件用 AND/OR 连接。
- 一条语句通常用分号结束，换行不是语句结束，SQL 换行也不需要反斜杠。
- ASC 升序、DESC 降序；排序只影响结果，不修改原数据。
- `LIMIT 1 OFFSET 1` 跳过第一条，再取一条。稳定分页需要确定的排序规则，必要时用唯一编号作为最后的排序字段。

#### 空值与统计

- NULL 表示没有值、未知或缺失，不等于 0，也不等于空字符串。
- 用 IS NULL / IS NOT NULL 判断缺失，不用 `= NULL`。
- 普通比较涉及 NULL 时通常得到 UNKNOWN；WHERE 仅保留结果为 TRUE 的记录。
- COUNT(*) 数结果行；COUNT(列名) 只数该列非 NULL 的值；AVG(列名) 忽略 NULL，不当作 0。
- GROUP BY 把相同分组键的记录归组；这里 NULL 会形成一个组，组内 COUNT(age) 可以是 0。
- WHERE 筛选参与分组的记录；HAVING 筛选分组统计后的结果。
- AS 定义结果别名；MySQL 允许在 HAVING 中引用本例的聚合别名。

#### 关联查询

- INNER JOIN 只保留匹配成功的组合。
- LEFT JOIN 保留左表记录；没有匹配记录时，右表列补为 NULL，不向原表插入记录。
- 统计实际行程次数用 COUNT(t.trip_id)。左连接后的 COUNT(*) 会把无行程学生保留的那一行也算进去。
- 为保留零次学生，“只匹配南湖出发的行程”应写在 ON 中；把这个右表条件写到 WHERE 会过滤掉未匹配的学生。
- 按学号区分同名学生，不能仅按姓名分组。
- 表别名 `student AS s`、`trip AS t` 仅在当前语句内使用，不修改真实表名。

综合示例：每个学生从南湖出发的行程次数，包括零次。

```sql
SELECT s.student_id, s.name, COUNT(t.trip_id) AS trip_count
FROM student AS s
LEFT JOIN trip AS t
ON s.student_id = t.student_id
   AND t.departure = '南湖'
GROUP BY s.student_id, s.name
ORDER BY s.student_id;
```

综合示例：全部行程次数前 3 名，次数相同按学号排序。

```sql
SELECT s.student_id, s.name, COUNT(t.trip_id) AS trip_count
FROM student AS s
LEFT JOIN trip AS t ON s.student_id = t.student_id
GROUP BY s.student_id, s.name
ORDER BY trip_count DESC, s.student_id ASC
LIMIT 3;
```

按课程中四名学生、三条行程的样例，第二条预期为小林 2 次、小周 1 次、小王 0 次。额外添加记录或重复插入行程会改变结果；这些是样例预期，不代表自动读取了当前数据库验证。

#### 事务

- START TRANSACTION 开始事务；COMMIT 提交；ROLLBACK 撤销当前连接里当前事务尚未提交的修改。
- 回滚范围按事务边界确定，不按选中多少代码、点几次执行按钮或最后一条语句确定。
- 已提交的历史修改不能通过后续 ROLLBACK 撤销。
- 当前事务能读到自己尚未提交的修改。两条 SELECT 会产生两份结果，不是只显示一个最终结果。
- 事务用于转账、订单与库存、预约与座位等必须配套完成的数据库操作。
- 事务不会自动判断业务成功：目标不存在时 UPDATE 可能影响 0 行，程序仍需检查结果。
- 普通语句失败不保证自动回滚整笔事务，程序需要明确处理提交与回滚。
- MySQL 许多 DDL 操作会隐式提交，不要把 CREATE/ALTER TABLE 当成普通可回滚的数据修改。
- 数据库事务不能撤销已经发送的短信或外部支付等动作；并发控制也需要另行设计。
- 保存点仅提及概念，尚未实践；锁、隔离级别、完整 ACID 原理尚未系统学习。

#### 索引

```sql
CREATE INDEX idx_student_name ON student(name);
SHOW INDEX FROM student;
```

- 普通索引帮助查找，允许重复姓名；唯一索引才限制非 NULL 索引值重复。
- 主键会建立主键索引，且主键不允许 NULL。
- 查询条件决定返回哪些记录，索引可能减少查找成本；数据库会自行选择执行方式。
- 索引不会合并同名记录，也不只返回第一条。小表不一定能观察到速度提升。
- 索引占空间，写入与索引列修改时也有维护开销。
- CREATE INDEX 不应重复运行同名创建语句。尚未进行执行计划或真实性能测试。

#### 2026-10-25 踩坑记录

| 问题 | 原因或判断 | 修正经验 |
|---|---|---|
| 追加代码后每次全量执行旧 SQL | 普通闪电未选择代码时会执行当前编辑区全部 SQL | 只选择本次要执行的完整语句；执行过的代码不会自动被跳过 |
| 测试学生似乎删不掉 | 可能查看了删除前的旧结果，也可能重复 INSERT 报错后未继续执行删除；不能仅凭代码断定原因 | 单独执行 DELETE 和 SELECT，检查 Action Output 的影响行数和错误信息 |
| 主键重复 | 重复插入已存在学号 | 不要重跑初始化；本次用限定学号的 ON DUPLICATE KEY UPDATE 修复练习状态 |
| 查询题写成显示年龄、筛选年龄 >=20 | SELECT 列表和题目条件没有分别对齐 | 先拆成“显示哪些列”和“筛选哪些行” |
| 包含周写成 `LIKE '周%'` | 混淆前缀与任意位置匹配 | 包含周用 `%周%` |
| WHERE 条件之间写逗号 | 把列举项目与逻辑条件混淆 | 列举用逗号，条件组合用 AND/OR |
| 统计次数写在 WHERE 中 | 聚合结果尚未生成 | 用 HAVING 筛选统计结果 |
| 综合查询漏 GROUP BY | 没有明确“每个学生分别统计” | 按学生标识分组，并列出需要的姓名 |
| LEFT JOIN 统计零次却使用 COUNT(*) | 左连接会为无匹配学生保留一行 | 统计非空的 t.trip_id |
| 要求保留零次，却保留 HAVING 次数 >=1 | 复制了旧需求的筛选条件 | 每次按新需求重新判断哪些条件需要删除 |
| 写成 `trip_countDESC1` | 别名、排序关键字和数量限制连在一起 | `ORDER BY trip_count DESC, s.student_id ASC LIMIT 3` |
| 把普通姓名索引理解为禁止同名 | 混淆查找辅助与唯一约束 | 普通索引允许同名，用学号区分学生 |
| 不理解一次执行为何出现 99 和 20 两份结果 | 两条 SELECT 各自产生执行当时的结果 | 切换结果标签；结果标签编号不是学生编号或新建表 |
| 认为回滚只撤销最近一条 SQL | 事务边界与语句范围混淆 | 从 START TRANSACTION 到提交/回滚判断范围 |

#### 下午阶段评价与当时的下一步

- 能正确回答 AND/OR、分页、外键与部分聚合判断；能独立写简单筛选和排序。
- 已在反馈后区分 WHERE/HAVING，但多表综合 SQL 仍需要指导，不能把看懂完整答案等同于独立完成。
- COUNT(*) 与 COUNT(列名) 在左连接中的区别反复出现，应安排短复习。
- 近期先补表设计、常用类型与约束、独立综合练习；再用 JDBC 完成 Java 保存学生并查询的最小闭环。
- 校园出行项目以能落地的数据读写为目标，复杂数据库集群与分库分表暂不展开。

<a id="jdbc-evening-2026-10-25"></a>

#### JDBC：Java 连接数据库与参数化查询（晚间补充）

下午完成 SQL 核心入门后，晚间继续创建 JdbcStudy 项目，将 Java 与 MySQL 连接起来。以下区分运行反馈、源码检查和仅提供示例的内容。

##### 已完成的练习

| 内容 | 实际进度 |
|---|---|
| Maven 项目 | 首次误建为普通 Java 项目，删除空项目后重新创建；已检查 pom.xml 与 src/main/java 等目录 |
| MySQL 驱动 | 使用 com.mysql:mysql-connector-j:9.7.0，学习者反馈依赖加载成功 |
| 数据库连接 | 学习者反馈“数据库连接成功”；理解地址、账号、密码与 Connection 的作用 |
| 环境变量 | 代码从 System.getenv("DB_PASSWORD") 读取密码；学习者反馈运行配置设置成功 |
| 参数化查询 | 按学号查询；曾遗漏参数设置，已调整为先 setString 再 executeQuery，修改后通过编译检查 |
| 查询成功与未找到 | 学习者反馈两个分支运行成功；用 found 标记是否读取到匹配记录 |
| 新增学生 | 已讲解 executeUpdate 并提供 InsertStudentTest 示例；尚未收到插入成功及数据库查询验证的反馈 |
| 修改、删除及 Java 事务 | 尚未完成 JDBC 实操，不把下午 SQL 事务实验计为 Java 事务实践 |

##### 核心执行过程

```text
读取连接信息与密码
→ 获取 Connection
→ 准备 SQL 和 PreparedStatement
→ 设置 ? 参数
→ executeQuery 执行查询
→ ResultSet.next 移动到结果行
→ getString 读取当前行字段
→ 自动关闭结果集、语句对象和连接
```

- Maven 管理项目依赖、编译、测试和打包，不属于 SSM。SSM 指 Spring、Spring MVC、MyBatis 的组合。
- JDBC 提供接口与 API，MySQL 驱动提供具体实现；Connection、PreparedStatement、ResultSet 的使用体现接口与多态。
- IDEA 启动 Java 程序；Workbench 是另一个数据库客户端，不必保持打开，但 MySQL 服务需要运行。
- JDBC URL 指向服务地址、端口和数据库，而非 D 盘数据文件路径；文件由 MySQL 管理。
- import 只引入类型名称，不负责下载驱动或建立连接。
- DB_PASSWORD 是环境变量名称，变量值才是密码。IDEA 分列表格填写名称和值；单行输入方式为 DB_PASSWORD=实际密码，不把真实密码写进源码或学习记录。
- 环境变量按运行配置设置；新建 InsertStudentTest 的运行配置时，需要另行确认是否传入 DB_PASSWORD。
- password 为 null 表示变量未找到，空字符串表示没有填写值；检查只判断有没有提供密码，不验证密码是否正确。
- Connection 代表数据库连接和会话的操作入口，不是整张表或数据库的副本。
- SQL 字符串仅保存文本；prepareStatement 创建执行对象；executeQuery 才执行查询。
- PreparedStatement 保存 SQL 与已设置的参数，因此调用无参 executeQuery() 时不再传入 SQL。
- 参数从 1 开始编号，setString 设置字符串，setInt 设置整数；必须在执行前设置参数。? 不需要写在 SQL 单引号中。
- ResultSet 初始位于第一行之前，next() 同时移动并返回 boolean；getString("name") 从当前行读取姓名，不移动行。
- SQL 决定返回哪些行和列，Java 遍历的是本次返回的结果；能匹配 SQL 条件不等于条件一定符合真正的业务需求。
- 当前默认的小结果集查询可按“驱动接收并在 Java 内存缓冲结果，再逐行读取”理解；next() 不代表重新执行 SELECT。流式或分批获取的配置暂不展开。
- 查询结果不会自动变成 Student 对象，需要读取字段并自行构造；对象映射尚未独立实践。
- try-with-resources 自动关闭资源。本例先关 ResultSet，再关 PreparedStatement，最后关 Connection；关闭连接不删除数据或停止 MySQL 服务。
- 外层 catch 不仅可能捕获连接错误，也可能捕获查询与读取错误；“数据库操作失败”比统一写“连接失败”更准确。此处记录建议，不宣称已修改该提示。
- 用户希望按真实代码每次解释少量语句。当前优先掌握执行流程、返回值、参数、读取与关闭；网络握手、驱动发现与预编译内部机制留待深入。

##### 查询与未找到提示的核心示例

放在已建立连接的代码内部使用；此段只查询，不修改数据库。密码仍由外层连接代码从环境变量读取。

```java
String sql = "SELECT student_id, name FROM student WHERE student_id = ?";

try (PreparedStatement statement = connection.prepareStatement(sql)) {
    statement.setString(1, "2026001");

    try (ResultSet resultSet = statement.executeQuery()) {
        boolean found = false;

        while (resultSet.next()) {
            found = true;

            String studentId = resultSet.getString("student_id");
            String name = resultSet.getString("name");
            System.out.println(studentId + "：" + name);
        }

        if (!found) {
            System.out.println("没有找到该学生");
        }
    }
}
```

用已存在学号 2026001 验证找到分支，用 not-exist-test 验证未找到分支。这里是经过讲解整理的学习示例，尚未将 JdbcStudy 工程源码上传到仓库。

##### 晚间踩坑与修正

| 疑问或错误 | 理清后的结论 |
|---|---|
| 把 Maven 当成 SSM | Maven 是构建和依赖管理工具；SSM 是三个框架的组合 |
| 运行配置只填密码，没有提供变量名 | 程序按 DB_PASSWORD 查找变量，必须同时配置名称与值 |
| SQL 改成带 ?，仍直接执行 | 参数未提供会报错；先创建 statement，设置参数，再创建执行查询的 ResultSet |
| 同一个 try 资源列表中立即执行查询 | 大括号之前资源已依次初始化，无法在中间设置参数；拆成嵌套 try |
| 不清楚等号右边调用什么 | statement.executeQuery() 调用执行对象的方法，返回 ResultSet；左边变量接收其引用 |
| 以为结果拿到后就在第一行 | 初始尚未选中数据行，必须先成功 next() 才读取字段 |
| 以为 next() 返回学生 | 它返回 boolean，学生字段通过当前行的 getter 获取 |
| 以为 while 结果为空仍执行一次 | 条件中的 next() 调用 1 次，循环体执行 0 次 |
| 以为每次 next() 都重新查询数据库 | 操作的是本次查询的结果；当前默认方式主要在缓冲结果中移动 |
| 不明白为什么只读到小林 | SQL 与参数只查询学号 2026001，循环不会自动读取其他未返回的学生 |
| 以为 next() 自动把 found 改为 true | 真正修改变量的是循环内的 found = true；next() 只决定是否进入循环 |
| 认为循环结束时 found 会变回 false | next() 与 found 是独立状态；有过匹配就保持 true，除非显式再次赋值 |
| 混淆“表有数据”和“查询有数据” | 查不到指当前条件返回零行，即使原表有其他学生也正常 |
| 查询和新增的执行方法混淆 | executeQuery 返回结果集；本次普通 INSERT 用 executeUpdate，成功新增一行预期返回 1 |
| 为避免每次输入密码想写死密码 | 改为运行配置环境变量；不提交带密码的 IDEA 配置 |

##### 本次阶段结论与下次起点

- 已完成连接与参数化查询的第一轮跟做，运行反馈确认连接、环境变量、找到与未找到分支。
- 参数设置顺序的修改曾通过 JDK 25 编译检查；没有把编译通过等同于完整数据库业务验收。
- 返回值、游标与循环执行顺序经过多轮解释才逐渐理解，仍需短练习巩固，不计为独立熟练掌握。
- 新增练习已提供完整示例，计划使用学号 2026005、姓名小陈、年龄 18。该普通 INSERT 重复运行会因主键冲突失败；是否已插入尚待确认。
- 下次先确认 InsertStudentTest 输出“新增行数：1”，并在 Workbench 查询到对应记录，再学习 JDBC 修改、删除。
- 当前仓库更新学习记录；本次不上传本地运行配置、密码或未检查的 Java 工程文件。

## 每次学习后的更新规则

1. 按实际学习日期追加记录，保留旧记录，不虚构未学习日期。
2. 同步更新学习进度、知识点索引和每日经验，避免只追加日志却漏改索引。
3. 区分已讲解、已跟做、独立完成、尚待验证；记录错误与修正，不只记录正确答案。
4. 新增 SQL 示例时说明运行前提、是否会修改数据以及重复执行的影响。
5. 每次学习收尾时整理并提交，不对没有发生的新学习做定时空更新。

