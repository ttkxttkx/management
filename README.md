# TLias 员工管理系统

Spring Boot + Vue 3 前后端分离的员工与部门管理系统。

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端 | Spring Boot 2.6.13 + MyBatis-Plus + MySQL |
| 前端 | Vue 3.4.33 + Vite |
| 部署 | Nginx 1.22.0（端口 90）|
| 认证 | JWT |
| 存储 | 阿里云 OSS |

## 项目结构

```
├── emp/                     # 后端模块
│   └── src/main/java/com/li/emp/
│       ├── controller/      # 控制器
│       ├── service/         # 服务层
│       ├── mapper/          # MyBatis 映射
│       ├── pojo/            # 实体类
│       ├── config/          # 配置类
│       └── utils/           # 工具类（JWT等）
├── nginx-1.22.0-web/        # 前端 + Nginx
└── pom.xml                  # Maven 父工程
```

## 快速启动

1. **数据库**：创建 MySQL 数据库 `tlias`，执行建表语句
2. **后端配置**：复制 `emp/src/main/resources/application.yml.example` 为 `application.yml`，填入你的数据库账号密码
3. **启动后端**：在项目根目录执行 `mvn spring-boot:run`
4. **启动前端**：进入 `nginx-1.22.0-web/` 启动 Nginx
5. **访问**：浏览器打开 `http://localhost:90`

## 注意事项

- `application.yml` 包含敏感信息，已通过 `.gitignore` 排除，不会上传到仓库
- 克隆后需自行创建 `application.yml`，参考 `application.yml.example` 模板
