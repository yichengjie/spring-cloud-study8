1. 下载jdk:1.8, zookeeper:3.5.7,kafka: 2.11.2 

2. 解压并配置jdk环境变量

3. 解压zookeeper，进入conf目录，复制zoo_sample.cfg 为zoo.cfg即可,启动命令```./zkServer.sh start```

4. 连接zookeeper执行命令```./zkCli.sh```

5. 停止zookeeper命令： ```./zkServer.sh stop```

6. 解压kafaka，进入config目录，修改server.properties

   ```a.listeners=PLAINTEXT://192.168.99.51:9092 .```

   ```b. advertised.listeners=PLAINTEXT://192.168.99.51:9092 ```

   ```c. zookeeper.connect=localhost:2181```

7. 启动kafka命令: ```bin/kafka-server-start.sh config/server.properties```

8. 停止kafka命令: ```./bin/kafka-server-stop.sh```

9. 创建topic命令: ```bin/kafka-topics.sh --create --zookeeper 192.168.99.51:2181 --replication-factor 1 --partitions 1 --topic hello-topic```

10. 查看已经创建的topic信息: ```bin/kafka-topics.sh --list --zookeeper 192.168.99.51:2181```

11. 发送消息：```bin/kafka-console-producer.sh --broker-list 192.168.99.51:9092 --topic hello-topic```

12. 接收消息：```bin/kafka-console-consumer.sh --bootstrap-server 192.168.99.51:9092 --topic hello-topic --from-beginning```

13. 打开防火墙端口
    ```
    a. firewall-cmd --zone=public --add-port=3306/tcp --permanent
    b. firewall-cmd --reload
    ```

    