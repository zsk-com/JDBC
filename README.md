# 基于JDBC的连接池

## 为什么封装？

**原生JDBC连接过程**：

~~~java
        //1导包
        //2加载驱动
        Class.forName("com.mysql.jdbc.Driver");  
        //3获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        //4创建状态参数
        PreparedStatement pstat = conn.prepareStatement(sql);
        //5执行数据库操作
        pstat.executeUpdate();
        //6关闭连接
        conn.close()
~~~

#### **原生JDBC连接存在的问题:**

  1.创建**连接时间很慢**(让整个执行过程很慢)
  2.**连接不能复用**(每一次都需要创建 DAO层 ，而DAO有好多方法， 每一个方法都需要JDBC)

#### **封装需要解决的问题：**

本质是**提高连接性能**、让**连接可复用**、减少代码的**冗余**

## 封装思路

#### **如何提高性能**？？？

​       1、开始可**创建好多个桥不拆** ，而这个桥当作一个资源复用

​       2、将创建好的多个桥放在一个**"大池子**"里，而这个池子用什么装载呢？显然是集合。所谓的连接池就是集合          			里装载着连接------》**集合<连接>**

#### **一个连接是否可以 同一个时间被好多人用？？？**

​	      显然不可以，**连接是独木桥**，能被使用的连接是空闲的

#### **如何能确定当前连接是否可用？？？** 

​         可设置一**状态**-------》属性(boolean值就挺好，false 连接空闲状态是可连接的，true是正在使用中)

#### **怎么做到连接复用？**？？

​        当要关闭连接时，不需要直接去关闭连接，而是将状态重置为false,这样将可以做到连接复用啦

## 扩展：

​    1.连接10个，连接池有几个？？？(设置**单例模式**)
​	2.连接加载驱动 账号名密码 不能固定？？？(通过**配置文件**)
​	3.多个人同时访问连接池？？？(需要控制**线程安全**)
​	4.如果加了锁，10个连接都被占用的情况下  第十一个人来了怎么办？？？(添加**排队等待机制**)
​	5.用了连接池以后 用户的使用方式完全变了？？？(使用**静态代理**，让用户使用的方式不变)

## 项目描述：

本项目是基于JDBC进行封装的连接池，为了加强自己的基础和能更深入解框架的本质， 自己实现了框架的常用功 能。本质是**提高连接性能**、让**连接可复用**、减少代码的**冗余**。

## 项目核心

核心逻辑在该类：/src/main/java/com/zsk/pool/**ConnectionPoor**.java ，该类下有详细的说明，想深入理解的同学可以看该类下的源码哦



## 使用连接池：

```java
引入依赖包
    <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
```

```java
在classpath是创建configure.properties
    
    
forName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/atm
user=root
apassword=root
nuberConn=2
    
备注：内容根据自己连接的数据库进行填写

```

```java
     	//获取连接
       Connection conn = ConnectionPoor.getPoor().getConnection();
        //创建状态参数
        PreparedStatement pstat = conn.prepareStatement(sql);
        //执行数据库操作
        pstat.executeQuery();
		//关闭连接 实际上不是真正的关闭
        conn.close();

```

