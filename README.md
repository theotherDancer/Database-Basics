# 数据库学习记录

记录 MySQL、SQL 与 Java JDBC 的学习过程、练习、常见错误及修正经验，后续结合校园出行项目继续巩固。

> 学习状态（更新至 2026-10-26）：SQL 与 JDBC 主要内容已完成第一轮学习，新增约束、表关系、子查询、三大范式及班次报名练习。当前以指导下实践为主，独立编写连接查询仍需巩固；StudentDao 已创建，方法迁移尚未开始。
> 本仓库保存学习笔记、SQL 整理示例及 JDBC 源码快照，不上传数据库物理文件、密码、连接密钥、IDEA 运行配置或真实用户数据。

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
| 独立综合查询 | 仍需巩固 | ON 匹配条件、分组统计、排序字段与 ASC，暂不计为独立熟练 |
| 类型、约束与表结构补充 | 已讲解并部分实践 | DATE/DATETIME、DECIMAL、ALTER TABLE、NOT NULL、UNIQUE、DEFAULT；CHECK 尚未实践 |
| 子查询与 EXISTS | 已讲解，需巩固 | IN、AVG 单值子查询、相关 EXISTS/NOT EXISTS、SELECT 1 |
| Maven 与 MySQL 驱动 | 已配置 | Maven 项目与 Connector/J 9.7.0 依赖加载成功 |
| JDBC 连接与查询 | 已跟做并反馈成功 | 环境变量、连接、参数化查询、结果遍历与未找到分支；仍需巩固 |
| JDBC 新增 | 学习者反馈运行成功 | InsertStudentTest；普通 INSERT 重复执行可能主键冲突 |
| JDBC 修改、删除与事务 | 已跟做并反馈成功 | executeUpdate；同一连接的 commit/rollback，两条事务路径均反馈成功 |
| 表关系与项目表设计 | 已进行指导练习 | 一对一、一对多、多对多、联合主键、三大范式、校园班次报名 |

## 仓库内容与运行说明

- [JdbcStudy](JdbcStudy)：原样保存的 Maven 学习工程（pom.xml 和 7 个 Java 文件）。
- [今日 SQL](sql/2026-10-26)：按结构、数据与约束实验、只读查询分为三份；由本次课程及 Workbench 草稿整理，不是原始草稿或数据库导出。
- 使用 JDK 25，IDEA 打开 JdbcStudy/pom.xml 加载 Maven；驱动版本由 pom.xml 声明。
- MySQL 本地数据库为 campus_learning。各 main 的运行配置需要设置 DB_PASSWORD 环境变量；变量名和密码值分开填写，不提交实际密码。
- ConnectionTest 提供学号查询、all 查询全部、0 退出；当前提示文字尚未列出 all，但分支已存在。
- InsertStudentTest/UpdateStudentTest/DeleteStudentTest 使用练习学号 2026005，分别新增小陈、修改年龄为 19、删除记录；按需要逐个运行，不是自动测试套件。
- TransactionTest 当前为成功路径，会将 2026001、2026002 的年龄改成 30、40 并提交。回滚演示曾将第二个学号改为不存在的 not-exist-test；已提交内容不能靠后续 rollback 撤销。
- SQL 建表、改结构和 INSERT 不宜在已有学习库整体重跑。故意报错和取消报名的语句已注释，需单独选择执行。班次编号应使用实际生成值。
- 本次仅编译检查，未重新运行数据库写入实验；本机 IDEA 工程、Workbench 草稿和数据库保持原状。

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
| JDBC 增删改、对象映射、循环与事务 | [10 月 26 日 JDBC](#jdbc-2026-10-26) |
| 类型、结构、约束与 NULL | [类型与约束](#constraints-2026-10-26) |
| 表关系、联合主键与三大范式 | [表设计](#design-2026-10-26) |
| 子查询、EXISTS、SELECT 1 | [子查询](#subquery-2026-10-26) |
| 班次报名与收尾查询 | [综合练习](#departure-2026-10-26) |
| 今日错误、验证范围与下次起点 | [踩坑与进度](#review-2026-10-26) |

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


### 2026-10-26：JDBC 实践、数据库补充与班次报名

> 这是在前一天记录基础上的后续进展；10 月 25 日的“待验证/待学习”保留为当时状态，以本日和顶部进度为最新状态。

<a id="jdbc-2026-10-26"></a>

#### JDBC：增删改、方法、对象与事务

- 学习者反馈新增、修改、删除运行成功，练习学号为 2026005。executeUpdate() 返回影响行数；返回 0 不必然抛异常。
- setInt(1, 19)、setString(2, "2026005") 分别绑定第一个、第二个问号；编号从 1 开始，参数是值，不是拼接 SQL 结构。
- DriverManager.getConnection(...) 获取连接；connection.prepareStatement(sql) 准备语句对象，不是再连一次数据库；设置参数后再 executeQuery/executeUpdate。
- 连续输入循环负责交互；结果集循环负责遍历本次查询结果。空输入 continue，0 退出，all 显示全部后 continue，避免作为学号再查询。
- findById(Connection, String) 将当前行映射成 Student；未找到返回 null，数据库异常通过 throws SQLException 向调用处传播，不能当成“没查到”。
- findAll(Connection) 将每行映射成 Student 并加入 List；没有数据返回空集合。主键查询可用 if(next())，全部查询使用 while(next())。
- Connection 是接口，参数接收已有连接对象的引用；main 创建连接，方法使用该连接。方法关闭自己创建的 statement/resultSet，外层负责关闭连接。
- Student 是独立 Java 对象。关闭结果集后它仍能使用；修改对象或清空 List 不会自动修改数据库。
- 事务在同一 Connection 上 setAutoCommit(false)，两个 UPDATE 均满足预期后 commit；失败时 rollback。
- 目标学生不存在时 UPDATE 可以正常返回 0；示例通过检查 rows != 1 并主动抛 SQLException 触发失败分支。
- 两条事务路径均收到学习者成功反馈：第二个学号不存在时回滚；改为 2026002 后提交。当前文件保存成功路径。
- 已提供恢复年龄的 UPDATE，但未在本次上传时查询数据库确认最终年龄，不宣称已恢复。

<a id="constraints-2026-10-26"></a>

#### 数据类型、表结构和约束

| 类型/规则 | 用途或结论 |
|---|---|
| VARCHAR(n) | 字符数上限；学号、手机号是标识，通常不用于计算，需保留前导零等内容 |
| INT | 保存整数；不会自动保证年龄合理 |
| DECIMAL(p,s) | 精确小数，p 为总位数，s 为小数位数；DECIMAL(5,2) 不能存 1234.56 |
| DATE / DATETIME | 日期 / 日期加时分秒；普通生日用 DATE |
| ADD COLUMN | 增加列；已反馈新增 birthday 成功 |
| MODIFY COLUMN | 修改列定义；已反馈 name 扩为 VARCHAR(100) 并保留 NOT NULL 成功 |
| NOT NULL | 禁止 NULL，但不禁止空字符串或空格 |
| UNIQUE | 非 NULL 值不重复；本例允许多个 NULL |
| DEFAULT | 插入省略列时使用默认值；不会替换明确传入的 NULL |
| PRIMARY KEY | 唯一且非空；一表一个主键，可以由多列组成 |
| FOREIGN KEY | 保证非空引用值对应的父记录存在，本身不要求外键值唯一 |

- birthday 已填写示例日期 2006-05-18，属于练习数据。
- account_demo 的 phone 只有 UNIQUE，可为 NULL；status 有 NOT NULL DEFAULT '正常'，明确写 NULL 不符合规则。是否允许 NULL 要逐列判断。
- 重复手机号实验收到插入失败反馈。其他已解释的实验，不一概计为逐项运行验收。
- 默认未配置级联的关联中，有子记录时直接删除父记录会被限制；取消报名应删除关联行。

<a id="design-2026-10-26"></a>

#### 表关系与三大范式

- 一对多：一个学生有多条行程，外键放在“多”的行程表。给该学号加 UNIQUE 将限制每名学生最多一条行程。
- 一对一示例：账户表学号使用 NOT NULL + UNIQUE + 外键；每个账户属于存在的学生，每名学生最多一个账户，但不保证人人有账户。
- 多对多：学生与课程通过 student_course 中间表连接；学生与班次通过 departure_registration 连接。
- 联合主键 (student_id, course_id) 限制整组重复，不限制每列单独重复。(2026001,101) 与 (2026002,101) 可以并存。
- “学生 + 车辆”不能区分同一辆车不同日期的发车；报名唯一性使用“学生 + 班次”。车辆管理如确有需要，可再独立建车辆表。
- 第一范式：不要在一格中拼接多个需分别处理的课程或号码；原子性结合业务需求理解。
- 第二范式：满足第一范式，消除非键字段对候选键的部分依赖。选课中姓名只依赖学号、课程名只依赖课程编号，应分表；成绩依赖学生与课程的组合。
- 第三范式：在本例中消除“学号 → 学院编号 → 学院名称/地址”的传递依赖；学院信息放学院表。
- 这些是教学示例和基础判断，不把看懂等同于能独立分析复杂范式。

<a id="subquery-2026-10-26"></a>

#### 子查询与 SELECT 1

- AND/OR 组合筛选条件；子查询提供一个值、一组记录或存在性判断，二者可以结合使用。
- IN 子查询：从选课表查出选了 101 的学号，再筛选学生；子查询无结果时外层返回零条。
- AVG 单值子查询：比较学生年龄是否高于整体平均值；AVG 忽略 NULL，无非空年龄时结果为 NULL。
- NULL 与平均值比较不会得到 TRUE，不满足 WHERE 条件。
- EXISTS 检查是否至少存在一条匹配记录；NOT EXISTS 查无选课学生、无人报名班次。
- 相关子查询通过外层学生/班次编号限制内层匹配；不能删掉这项关联后仍声称在逐个判断。
- SELECT 1 返回固定值 1；不表示查一条、字段编号或统计数量。EXISTS 仅关心有没有结果，不需要读取具体字段。
- “先内后外”是理解示例的思路，实际执行方式由优化器决定；部分子查询可改写为 JOIN。

<a id="departure-2026-10-26"></a>

#### 校园班次报名综合练习

1. 沿用 student，建立 departure 与 departure_registration；学习者反馈建表成功。
2. 添加 2026-10-27 14:00 南湖到马房山班次；学习者确认自动编号为 1。
3. 示例将小林报名到班次 1，随后讲解小周报名、三表详情查询、报名人数与取消报名。
4. LEFT JOIN 保留零报名班次，COUNT(r.student_id) 忽略右侧补出的 NULL；COUNT(*) 会数保留行。
5. GROUP BY 按班次分组；HAVING COUNT(...) >= 2 筛选至少两人报名的班次。
6. 取消单次报名同时限制 student_id 与 departure_id；只限制学号会取消该学生全部报名，不删除学生或班次本身。
7. NOT EXISTS 查询无人报名班次；报名表还有任意一条匹配记录就不符合条件。
8. 收尾题：按学号升序显示 1 号班次报名学生。学习者能回答班次 id=1，ON 条件需要提示，ASC 曾写成 esc，最终提供完整参考答案。

具体 SQL 见 [今日脚本](sql/2026-10-26)。脚本是分段学习材料，包含修改数据的操作；故意报错语句已经注释，不应整体重复执行。
整理版使用 LAST_INSERT_ID() 保存新班次编号，避免假定一直为 1；这是本次整理补充，不计为已教学验收内容。

<a id="review-2026-10-26"></a>

#### 今日踩坑、验证与下次起点

| 问题 | 结论/修正经验 |
|---|---|
| 循环里重复声明变量，或声明前使用 | 在本轮输入后使用本轮参数；分清变量作用域和输入循环、读取循环 |
| 把参数绑定当成拼接字符串 | 问号的位置来自 SQL，setter 根据序号和类型传入数据值 |
| 以为上一次 NULL 能存，这次也能 | 每列约束不同；phone 可空，status 非空 |
| 联合主键中一列相同就以为重复 | 检查整组，两个分量都相同才是本例的重复报名 |
| 把车辆唯一性当报名唯一性 | 同一车辆可以多次发车，用班次区分具体乘车安排 |
| SELECT 1 被误解为只查一条 | 它是固定值表达式；EXISTS 关注有没有匹配行 |
| 不知道多条件与子查询区别 | 逻辑连接条件与查询提供数据是两个维度 |
| ON 和 WHERE 职责混淆 | 本次内连接练习中 ON 写关联条件，WHERE 写目标班次筛选 |
| 排序只写 esc | ORDER BY 要写字段；升序 ASC，降序 DESC |
| 六百行集中同一标签页 | 保存为 SQL 文件，按主题分标签；新标签确认默认库，勿整体重跑旧代码 |
| 误把一条 SQL 理解为一行 | 通常以分号划分语句，换行只是排版；当前语句快捷键 Ctrl+Enter |

**本次上传验证：**
- 实际读取 7 个 Java 源文件及 pom.xml；全部通过本机 JDK 25 javac 编译。
- 上传源码保持学习现场，不擅自重构；StudentDao 实际为空类，findById/findAll 仍在 ConnectionTest。
- 保留的待改进项：StudentId 字段首字母大写；Update/Delete 捕获提示仍写“新增失败”；ConnectionTest 提示未列出 all，catch 提示“连接失败”覆盖范围偏窄。
- 编译通过不等于运行验收。运行成功依据学习者当时反馈；本次不连接数据库重跑写入，也未独立核对现有表的最终数据。
- 没有上传 IDEA 配置、密码、数据库物理文件或 target。

**阶段结论：** Level 1“MySQL 数据库与 JDBC 编程”主要内容完成第一轮覆盖，复杂查询、独立表设计仍需指导；CHECK、完整类型体系、锁与隔离、执行计划、备份恢复尚未系统实践，不标成全部精通。

**下一次从这里继续：** 先把 findById 搬入已经创建的 StudentDao，改为 StudentDao.findById(connection, inputId) 调用并验证；再整理其他操作、编写控制台菜单。尚未完成 DAO 迁移，也未进入 Spring/SSM。


## 每次学习后的更新规则

1. 按实际学习日期追加记录，保留旧记录，不虚构未学习日期。
2. 同步更新学习进度、知识点索引和每日经验，避免只追加日志却漏改索引。
3. 区分已讲解、已跟做、独立完成、尚待验证；记录错误与修正，不只记录正确答案。
4. 新增 SQL 示例时说明运行前提、是否会修改数据以及重复执行的影响。
5. 每次学习收尾时整理并提交，不对没有发生的新学习做定时空更新。

