# ================= 第一阶段：构建 Maven 项目 =================
FROM   maven:3.9.12-amazoncorretto-17 AS build

WORKDIR /workspace/app
COPY settings-docker.xml /usr/share/maven/ref/settings-docker.xml

ARG ENV=product
# 只复制父 pom.xml 和模块目录
COPY pom.xml .
COPY src src

RUN echo "Environment: $ENV"

# 执行 Maven 构建（跳过测试、指定 profile）
RUN mvn clean package -Dmaven.test.skip=true -P${ENV} -B \
   -s /usr/share/maven/ref/settings-docker.xml

# ================= 第二阶段：运行环境 =================
FROM amazoncorretto:17.0.17-alpine3.21

# 设置工作目录
WORKDIR /app

# 从构建阶段复制 JAR 文件
COPY --from=build /workspace/app/target/java-translation.jar ./app.jar
ENV PORT  7006
# 暴露应用监听的端口
EXPOSE $PORT

# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 创建日志和 dump 目录
RUN mkdir -p /app/logs /app/dumps
# ================= Java 17 兼容的 JVM 参数 =================
# 基础内存配置（简化版，先确保能启动）
ENV JAVA_OPTS="-Xms512g -Xmx1g -XX:+UseG1GC"

# 启动命令（简化版）
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar \
            --server.port=$PORT \
            --server.servlet.context-path=/"]