# WeiXinSport 安全配置指南

## 概述

本文档提供WeiXinSport项目的安全配置指南，帮助您安全地部署和运行应用。

## 1. 环境变量配置

### 1.1 创建环境变量文件

1. 复制环境变量示例文件：
   ```bash
   cp .env.example .env
   ```
   Windows系统：
   ```cmd
   copy .env.example .env
   ```

2. 编辑 `.env` 文件，修改所有配置值：
   ```bash
   # 使用文本编辑器打开 .env 文件
   ```

### 1.2 关键安全配置

#### 数据库密码
- `MYSQL_ROOT_PASSWORD`: MySQL root用户密码（必须使用强密码）
- `DATABASE_PASSWORD`: 应用数据库用户密码

#### JWT密钥
- `JWT_SECRET_KEY`: JWT签名密钥（必须使用强随机字符串）
  - 生成命令：`openssl rand -base64 32`
  - 或使用：`date +%s | sha256sum | base64 | head -c 32`

#### 密码加密
- `PASSWORD_SALT`: MD5加密盐值

## 2. 数据库安全

### 2.1 修改默认密码

初始化脚本 `sql/init.sql` 中包含默认用户：
- 学生账号：`2022001`，密码：`123456`
- 教师账号：`T001`，密码：`123456`
- 管理员账号：`admin`，密码：`123456`

**部署后必须修改这些默认密码！**

### 2.2 数据库访问控制

1. **限制网络访问**：
   - 生产环境中，数据库应仅允许应用服务器访问
   - 使用防火墙规则限制3306端口访问

2. **使用独立应用账号**：
   - 不要使用root账号运行应用
   - 创建只具有必要权限的数据库用户

3. **启用SSL连接**（可选）：
   ```yaml
   DATABASE_URL: jdbc:mysql://localhost:3306/sportapp?useSSL=true&requireSSL=true
   ```

## 4. JWT安全

### 4.1 密钥管理
- **不要使用默认密钥**：默认密钥仅用于开发环境
- **定期轮换密钥**：建议每3-6个月轮换一次JWT密钥
- **密钥存储**：将密钥存储在环境变量或密钥管理服务中

### 4.2 Token配置
- 过期时间：建议设置为6-24小时（通过 `JWT_EXPIRE_HOURS` 配置）
- 使用HTTPS：生产环境必须使用HTTPS传输Token

## 5. 网络与部署安全

### 5.1 Docker安全
1. **使用非root用户运行容器**：
   ```dockerfile
   USER 1000:1000
   ```

2. **限制容器权限**：
   ```yaml
   # docker-compose.yml
   services:
     backend:
       security_opt:
         - no-new-privileges:true
       read_only: true
   ```

3. **定期更新基础镜像**：
   ```dockerfile
   FROM eclipse-temurin:17-jdk-alpine
   ```

### 5.2 健康检查
Docker Compose已配置健康检查，监控服务状态：
- MySQL：通过mysqladmin ping检测
- Redis：通过redis-cli ping检测
- 后端：通过/actuator/health端点检测

## 6. 日志与监控

### 6.1 安全日志
- 启用登录审计日志
- 记录敏感操作（密码修改、数据删除等）
- 监控异常登录尝试

### 6.2 Spring Boot Actuator
已添加Actuator依赖，提供健康检查端点：
- `/actuator/health`：应用健康状态
- `/actuator/info`：应用信息

**生产环境注意事项**：
- 限制Actuator端点的访问
- 不要暴露敏感端点（如 `/actuator/env`, `/actuator/beans`）

## 7. 定期安全维护

### 7.1 定期任务
1. **更新依赖**：定期更新Maven依赖，修复安全漏洞
2. **密码轮换**：定期轮换数据库密码和JWT密钥
3. **审计日志**：定期审查安全日志
4. **备份验证**：定期测试数据库备份恢复

### 7.2 安全扫描
1. **依赖扫描**：使用 `mvn dependency:check` 检查依赖漏洞
2. **容器扫描**：使用Trivy或Anchore扫描Docker镜像
3. **代码扫描**：使用SonarQube进行代码安全分析

## 8. 紧急响应

### 8.1 发现漏洞时
1. 立即更改所有相关密码和密钥
2. 审查受影响系统的日志
3. 更新相关依赖版本
4. 通知可能受影响的用户

### 8.2 数据泄露时
1. 立即隔离受影响系统
2. 保留证据用于调查
3. 根据法规要求通知相关方
4. 实施补救措施

## 9. 联系与支持

如有安全问题，请联系项目维护者。

**重要提醒**：安全是一个持续的过程，请定期审查和更新安全配置。