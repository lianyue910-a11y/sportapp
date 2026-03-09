FROM eclipse-temurin:17-jdk-alpine
# 设置时区为上海
RUN apk add --no-cache tzdata
ENV TZ=Asia/Shanghai
# 挂载目录
VOLUME /tmp

# 复制 jar 包
COPY target/WeiXinSport-0.0.1-SNAPSHOT.jar app.jar
# 暴露端口
EXPOSE 8080

# 启动命令
ENTRYPOINT ["java", "-jar", "/app.jar"]