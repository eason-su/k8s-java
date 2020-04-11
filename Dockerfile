
#基础镜像，如果本地仓库没有，会从远程仓库拉取
FROM openjdk:8-jdk-alpine
#容器中创建目录
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /app/app.jar
WORKDIR /app/
EXPOSE 8080
ENTRYPOINT ["java","-jar","./app.jar"]

# docker run -d -p 8080:8080 registry.cn-hangzhou.aliyuncs.com/easonsu/k8s-java:0.0.1-SNAPSHOT
