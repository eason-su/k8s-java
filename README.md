# k8s-java
k8s test



# Dockerfile 相关参数解释
https://www.cnblogs.com/qdhxhz/p/9926293.html

1、FORM，这是引入一个父镜像，在此基础上进行添加只读层。之前我写过，镜像可以理解成由一层层只读层组成，FORM下面的命令，可以理解就是在已有的只读层，添加只读层。FORM可以有多个，但最上面的一定是FROM命令。

2、VOLUME，配置一个具有持久化功能的目录，主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp。该步骤是可选的。
通俗解释：默认情况下，容器不使用任何 volume，此时，容器的数据被保存在容器之内，它只在容器的生命周期内存在，会随着容器的被删除而被删除。
因为如果你不想在容器删除后，容器的数据也被删除，那么就可以指定持久化目录。它被设计用来保存数据，而不管容器的生命周期。因此，当你删除一个容器时，Docker 肯定不会自动地删除一个volume。

3、ARG, 设置编译镜像时加入的参数。 这里的JAR_FILE就是maven插件中的<JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>

4、COPY，只支持将本地文件复制到容器 ,还有个ADD更强大但复杂点。

5、ENTRYPOINT 容器启动时执行的命令。这里就是java -jar app.jar

当然Dockerfile的命令说明官网有标准文档，以后有需要会再回过来查看官方文档。

[Dockerfile官方文档] (https://docs.docker.com/engine/reference/builder/#usage)

https://docs.docker.com/engine/reference/builder/


# springboot  docker 指南

https://spring.io/guides/topicals/spring-boot-docker



# dockerfile-maven-plugin 插件使用方法
https://github.com/spotify/dockerfile-maven




# 本地编译docker镜像

一、创建工程文件
1、正常创建一个springboot工程
2、创建一个TestController测试类，用于在我们部署docker之后访问验证使用
3、创建Dockerfile文件

二、打包和测试
1、先单纯的打包工程，验证测试类是否能正常访问

2、配置docker主机，开放远程端口
我上面的操作都是在自己的电脑windows上操作的，这步说的docker主机是我一个Linux主机，已经正常安装过docker服务，下面修改配置然后重启服务
```
 vim编辑docker配置文件/lib/systemd/system/docker.service，并修改ExecStart为下面的内容
 vim /lib/systemd/system/docker.service
 ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock
```

修改后，然后重启docker服务
```
# 1，加载docker守护线程
systemctl daemon-reload
# 2，重启docker 
systemctl restart docker

```
重启服务后，在你springboot-docker工程的电脑上使用telnet进行测试2375端口是否开启成功

3、为你打包工程的电脑配置环境变量，添加DOCKER_HOST，值为tcp://192.168.11.88:2375
4、使用maven编译打包镜像
打开cmd窗口，确定环境变量配置生效：输入 echo %DOCKER_HOST%，会输出 tcp://192.168.11.88:2375
然后使用命令 mvn clean package dockerfile:build -Dmaven.test.skip=true 编译项目并构建docker镜像，编译结束自动推送镜像到docker主机中。
首次执行，会比较慢，慢慢等待…
编译过程如下：
```
"C:\Program Files\Java\jdk1.8.0_171\bin\java.exe" -Dmaven.multiModuleProjectDirectory=E:\work\code\study\kj-workspace\k8s-java "-Dmaven.home=D:\Program Files\JetBrains\IntelliJ IDEA 2019.3\plugins\maven\lib\maven3" "-Dclassworlds.conf=D:\Program Files\JetBrains\IntelliJ IDEA 2019.3\plugins\maven\lib\maven3\bin\m2.conf" "-Dmaven.ext.class.path=D:\Program Files\JetBrains\IntelliJ IDEA 2019.3\plugins\maven\lib\maven-event-listener.jar" "-javaagent:D:\Program Files\JetBrains\IntelliJ IDEA 2019.3\lib\idea_rt.jar=49480:D:\Program Files\JetBrains\IntelliJ IDEA 2019.3\bin" -Dfile.encoding=UTF-8 -classpath "D:\Program Files\JetBrains\IntelliJ IDEA 2019.3\plugins\maven\lib\maven3\boot\plexus-classworlds-2.6.0.jar" org.codehaus.classworlds.Launcher -Didea.version2019.3 com.spotify:dockerfile-maven-plugin:1.4.10:build
[INFO] Scanning for projects...
[INFO] 
[INFO] -------------------------< com.eason:k8s-java >-------------------------
[INFO] Building k8s-java 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- dockerfile-maven-plugin:1.4.10:build (default-cli) @ k8s-java ---
[INFO] dockerfile: E:\work\code\study\kj-workspace\k8s-java\Dockerfile
[INFO] contextDirectory: E:\work\code\study\kj-workspace\k8s-java
[INFO] Building Docker context E:\work\code\study\kj-workspace\k8s-java
[INFO] Path(dockerfile): E:\work\code\study\kj-workspace\k8s-java\Dockerfile
[INFO] Path(contextDirectory): E:\work\code\study\kj-workspace\k8s-java
[INFO] 
[INFO] Image will be built as registry.cn-hangzhou.aliyuncs.com/easonsu/k8s-java:0.0.1-SNAPSHOT
[INFO] 
[INFO] Step 1/7 : FROM openjdk:8-jdk-alpine
[INFO] 
[INFO] Pulling from library/openjdk
[INFO] Digest: sha256:94792824df2df33402f201713f932b58cb9de94a0cd524164a0f2283343547b3
[INFO] Status: Image is up to date for openjdk:8-jdk-alpine
[INFO]  ---> a3562aa0b991
[INFO] Step 2/7 : VOLUME /tmp
[INFO] 
[INFO]  ---> Using cache
[INFO]  ---> aa564cb55396
[INFO] Step 3/7 : ARG JAR_FILE
[INFO] 
[INFO]  ---> Using cache
[INFO]  ---> afb728d914cd
[INFO] Step 4/7 : ADD ${JAR_FILE} /app/app.jar
[INFO] 
[INFO]  ---> Using cache
[INFO]  ---> 5bc8f262193b
[INFO] Step 5/7 : WORKDIR /app/
[INFO] 
[INFO]  ---> Using cache
[INFO]  ---> b3b412c57f07
[INFO] Step 6/7 : EXPOSE 8080
[INFO] 
[INFO]  ---> Using cache
[INFO]  ---> b86f13d308d0
[INFO] Step 7/7 : ENTRYPOINT ["java","-jar","./app.jar"]
[INFO] 
[INFO]  ---> Using cache
[INFO]  ---> 60f9983db5d4
[INFO] Successfully built 60f9983db5d4
[INFO] Successfully tagged registry.cn-hangzhou.aliyuncs.com/easonsu/k8s-java:0.0.1-SNAPSHOT
[INFO] 
[INFO] Detected build of image with id 60f9983db5d4
[INFO] Building jar: E:\work\code\study\kj-workspace\k8s-java\target\k8s-java-0.0.1-SNAPSHOT-docker-info.jar
[INFO] Successfully built registry.cn-hangzhou.aliyuncs.com/easonsu/k8s-java:0.0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  26.664 s
[INFO] Finished at: 2020-04-12T05:25:21+08:00
[INFO] ------------------------------------------------------------------------

```

https://shanhy.blog.csdn.net/article/details/89645254



# 常见问题处理

https://blog.csdn.net/nullpointer2008/article/details/102384049




# k8s

kubectl create -f k8s.yaml




# k8s ingress

https://www.jianshu.com/p/97dd4d59ac5a
