# TLias 员工管理系统

基于 Spring Boot + Vue 3 的前后端分离员工与部门管理系统。

## 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| 后端框架 | Spring Boot | 2.6.13 |
| ORM | MyBatis-Plus | — |
| 数据库 | MySQL | 8.0+ |
| 身份认证 | JWT (io.jsonwebtoken) | 0.9.1 |
| 对象存储 | 阿里云 OSS SDK | 3.17.4 |
| AOP 日志 | Spring AOP | — |
| 前端框架 | Vue 3 + Vite | 3.4.33 |
| Web 服务器 | Nginx | 1.22.0 |
| 构建工具 | Maven | — |
| JDK | Java | 1.8 |

## 项目结构

```
tlias-project/
│
├── emp/                                    # 后端 Spring Boot 模块
│   └── src/main/java/com/li/emp/
│       ├── AOP/                            # AOP 演示切面
│       │   └── AOPdemo.java
│       ├── annotation/                     # 自定义注解
│       │   └── OperateLog.java             # 操作日志注解
│       ├── aspect/                         # 切面实现
│       │   └── OperateLogAspect.java       # 操作日志环绕切面
│       ├── config/                         # 配置类
│       │   ├── MyMetaObjectHandler.java    # 自动填充 createTime/updateTime
│       │   ├── MybatisPlusConfig.java      # MyBatis-Plus 分页插件
│       │   └── WebConfig.java              # 拦截器注册 + 静态资源配置
│       ├── controller/                     # 控制器层
│       │   ├── DeptController.java         # 部门 CRUD
│       │   ├── EmpController.java          # 员工 CRUD + 分页查询
│       │   ├── loginContaoller.java        # 登录认证
│       │   └── uploadContraller.java       # 文件上传
│       ├── dto/                            # 数据传输对象
│       │   └── EmpDTO.java                 # 员工分页查询参数
│       ├── filter/                         # Servlet 过滤器
│       │   └── filter.java                 # JWT 过滤器
│       ├── interceptor/                    # Spring 拦截器
│       │   └── LoginCheckInterceptor.java  # 登录检查拦截器
│       ├── mapper/                         # 数据访问层
│       │   ├── DeptMapper.java
│       │   └── EmpMapper.java
│       ├── pojo/                           # 实体类
│       │   ├── Dept.java                   # 部门实体
│       │   ├── EmpEntity.java              # 员工实体
│       │   ├── EmpExpr.java                # 员工工作经历
│       │   ├── LoginDTO.java               # 登录请求体
│       │   ├── Result.java                 # 统一响应包装
│       │   └── data.java                   # 分页数据容器
│       ├── service/                        # 服务接口
│       │   ├── DeptService.java
│       │   ├── EmpService.java
│       │   └── LoginService.java
│       │   └── impl/                       # 服务实现
│       │       ├── DeptServiceImpl.java
│       │       ├── EmpServiceImpl.java
│       │       └── LoginServiceImpl.java
│       └── utils/                          # 工具类
│           ├── JwtUtils.java               # JWT 生成与解析
│           └── UserContext.java            # ThreadLocal 用户上下文
│   └── src/main/resources/
│       ├── application.yml.example         # 配置模板（可提交到 Git）
│       ├── application.yml                 # 真实配置（已排除，不上传）
│       ├── mapper/                         # MyBatis XML 映射文件
│       │   ├── DeptMapper.xml
│       │   └── EmpMapper.xml
│       └── static/                         # 静态资源
│           ├── index.html
│           └── upload.html
│
├── aliyun-oss-spring-boot-autoconfigrator/ # 阿里云 OSS 自动配置模块（骨架）
├── aliyun-oss-spring-boot-starter/         # 阿里云 OSS Starter 模块（骨架）
│
├── nginx-1.22.0-web/                       # 前端 + Nginx 部署
│   └── nginx-1.22.0-web/
│       ├── conf/nginx.conf                 # Nginx 配置
│       ├── html/                           # 前端静态资源
│       │   ├── index.html                  # Vue SPA 入口
│       │   └── assets/                     # 打包后的 JS/CSS/图片
│       └── nginx.exe                       # Nginx 可执行文件
│
├── pom.xml                                 # Maven 父 POM
└── .gitignore                              # Git 忽略规则
```

## 功能特性

### 部门管理
- 部门列表查询
- 新增部门
- 按 ID 查询部门（编辑回显）
- 修改部门信息
- 删除部门
- 自动填充创建时间和更新时间

### 员工管理
- 员工分页查询（支持按姓名、性别筛选）
- 员工列表查询（不分页）
- 新增员工
- 按 ID 查询员工（编辑回显）
- 修改员工信息（仅更新非空字段）
- 批量删除员工
- 自动填充创建时间和更新时间

### 登录认证
- 用户名 + 密码登录
- JWT 令牌签发
- 双层请求拦截（Filter + Interceptor 双重 JWT 校验）
- ThreadLocal 用户上下文传递
- 未登录请求自动返回 401

### 文件上传
- 支持 multipart/form-data 上传
- 文件自动重命名（UUID 前缀）
- 文件大小限制：单文件 3MB，总请求 11MB

### 操作日志
- Spring AOP 环绕切面记录每次请求
- 记录操作人、方法名、参数、返回值、耗时
- 可扩展的自定义 `@OperateLog` 注解

## 系统架构

```
浏览器 (http://localhost:90)
       │
       ▼
   Nginx :90
       │
       ├── / → 静态文件 (Vue SPA)
       │         └── try_files $uri /index.html (SPA 路由回退)
       │
       └── /api/* → rewrite 掉 /api 前缀 → proxy_pass
                        │
                        ▼
               Spring Boot :8080
                        │
               ┌───────┴───────┐
               │  JwtFilter    │  ← Servlet Filter 层 JWT 校验
               │  (/* 全部请求) │
               └───────┬───────┘
                       │
               ┌───────┴───────┐
               │  LoginCheck   │  ← HandlerInterceptor 层 JWT 校验
               │  Interceptor  │     排除 /login 和静态资源
               └───────┬───────┘
                       │
               ┌───────┴───────┐
               │  Controller   │  ← DeptController / EmpController
               └───────┬───────┘      loginContaoller / uploadContraller
                       │
               ┌───────┴───────┐
               │  AOP 切面      │  ← 操作日志环绕通知
               └───────┬───────┘
                       │
               ┌───────┴───────┐
               │   Service     │  ← 业务逻辑层 (事务管理)
               └───────┬───────┘
                       │
               ┌───────┴───────┐
               │   Mapper      │  ← MyBatis-Plus / XML 映射
               └───────┬───────┘
                       │
                       ▼
                 MySQL :3306
               数据库: tlias
```

## API 接口文档

所有接口统一返回格式：
```json
{
  "code": 1,       // 1=成功, 0=失败
  "msg": "success",
  "data": { ... }
}
```

### 部门接口 `/depts`

| 方法 | 路径 | 说明 |
|------|------|------|
| `GET` | `/depts` | 查询所有部门列表 |
| `GET` | `/depts/{id}` | 按 ID 查询单个部门 |
| `POST` | `/depts` | 新增部门 |
| `PUT` | `/depts` | 修改部门 |
| `DELETE` | `/depts?id={id}` | 删除部门 |

### 员工接口 `/emps`

| 方法 | 路径 | 说明 |
|------|------|------|
| `GET` | `/emps` | 分页查询员工（参数：`name`, `gender`, `page`, `pageSize`） |
| `GET` | `/emps/list` | 查询全部员工（不分页） |
| `GET` | `/emps/{id}` | 按 ID 查询员工 |
| `POST` | `/emps` | 新增员工 |
| `PUT` | `/emps` | 修改员工 |
| `DELETE` | `/emps?ids=1&ids=2` | 批量删除员工 |

### 登录接口

| 方法 | 路径 | 说明 |
|------|------|------|
| `POST` | `/login` | 登录，参数 `{ "username": "...", "password": "..." }`，返回含 JWT 的员工信息 |

### 上传接口

| 方法 | 路径 | 说明 |
|------|------|------|
| `POST` | `/upload` | 文件上传（multipart/form-data），字段：`name`, `age`, `file` |

## 数据库

### emp 表（员工）

| 字段 | 类型 | 说明 |
|------|------|------|
| `id` | INT (自增) | 主键 |
| `username` | VARCHAR | 登录用户名 |
| `password` | VARCHAR | 登录密码 |
| `name` | VARCHAR | 姓名 |
| `gender` | TINYINT | 性别：1=男，2=女 |
| `phone` | VARCHAR | 手机号 |
| `job` | TINYINT | 职位：1=班主任，2=讲师，3=学工主管，4=教研主管，5=咨询师 |
| `salary` | INT | 薪资 |
| `image` | VARCHAR | 头像 URL |
| `entry_date` | DATE | 入职日期 |
| `dept_id` | INT | 所属部门 ID |
| `create_time` | DATETIME | 创建时间 |
| `update_time` | DATETIME | 更新时间 |

### dept 表（部门）

| 字段 | 类型 | 说明 |
|------|------|------|
| `id` | INT (自增) | 主键 |
| `name` | VARCHAR | 部门名称 |
| `create_time` | DATETIME | 创建时间 |
| `update_time` | DATETIME | 更新时间 |

## 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/ttkxttkx/management.git
cd management
```

### 2. 配置数据库

创建 MySQL 数据库 `tlias`，设置字符集 utf8mb4：

```sql
CREATE DATABASE IF NOT EXISTS tlias DEFAULT CHARACTER SET utf8mb4;
```

然后创建 `emp` 和 `dept` 表（建表语句略，请根据字段说明自行创建，或使用 MyBatis-Plus 自动建表）。

### 3. 配置后端

```bash
# 复制配置模板并填入你的数据库账号密码
cp emp/src/main/resources/application.yml.example emp/src/main/resources/application.yml
```

编辑 `application.yml`，将 `username` 和 `password` 改为你自己的数据库账号。

### 4. 启动后端

```bash
# 在项目根目录执行
mvn clean package -DskipTests
java -jar emp/target/emp-*.jar
```

后端默认运行在 `http://localhost:8080`。

### 5. 启动前端

```bash
cd nginx-1.22.0-web/nginx-1.22.0-web
./nginx.exe
```

Nginx 监听端口 90，前端访问 `http://localhost:90`。

### 6. 访问系统

浏览器打开 `http://localhost:90`，使用员工账号登录。

## 安全说明

- `application.yml` 包含数据库密码等敏感信息，已加入 `.gitignore`，**不会被提交到 Git 仓库**
- 克隆后请参考 `application.yml.example` 自行创建配置文件
- JWT 令牌用于身份认证，每个请求需携带 `token` 请求头
- 建议生产环境使用环境变量注入敏感信息（如 `${DB_PASSWORD}`）
