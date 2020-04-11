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
