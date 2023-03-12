### 创建密钥仓库以及CA
1. 创建密匙仓库,用户存储证书文件
    ```text
    keytool -keystore server.keystore.jks -alias hello_kafka -validity 100000 -genkey
    ```
2. 创建CA
    ```text
    openssl req -new -x509 -keyout ca-key -out ca-cert -days 100000
    ```
3. 将生成的CA添加到客户端信任库
    ```text
    keytool -keystore client.truststore.jks -alias CARoot -import -file ca-cert
    ```
4. 为broker提供信任库以及所有客户端签名了密钥的CA证书
    ```text
    keytool -keystore server.truststore.jks -alias CARoot -import -file ca-cert
    ```
### 签名证书，用自己生成的CA来签名前面生成的证书
1. 签名证书，用自己生成的CA来签名前面生成的证书
    ```text
    keytool -keystore server.keystore.jks -alias hello_kafka -certreq -file cert-file
    ```
2. 用CA签名：
   ```text
   openssl x509 -req -CA ca-cert -CAkey ca-key -in cert-file -out cert-signed -days 100000 -CAcreateserial -passin pass:hello123
   ```
3. 导入CA的证书和已签名的证书到密钥仓库
   ```text
   keytool -keystore server.keystore.jks -alias CARoot -import -file ca-cert
   keytool -keystore server.keystore.jks -alias hello_kafka -import -file cert-signed
   ```
### kafka集成ssl (服务端配置)
1. 修改config/server.properties配置文件
   ```text
   listeners=PLAINTEXT://192.168.99.51:9092,SSL://192.168.99.51:8989
   advertised.listeners=PLAINTEXT://192.168.99.51:9092,SSL://192.168.99.51:8989
   ssl.keystore.location=/root/tools/ca_temp/server.keystore.jks
   ssl.keystore.password=hello123
   ssl.key.password=hello123
   ssl.truststore.location=/root/tools/ca_temp/server.truststore.jks
   ssl.truststore.password=hello123
   ```
2. 重启kafka
3. 使用openssl测试ssl端口
   ```text
   openssl s_client -debug -connect 192.168.99.51:8989 -tls1
   ```
4. 打开防火墙端口
   ```text
   a. firewall-cmd --zone=public --add-port=8989/tcp --permanent
   b. firewall-cmd --reload
   ```
### kafka客户端ssl配置
1. 配置修改
   ```text
   security.protocol=SSL
   ssl.endpoint.identification.algorithm=
   ssl.truststore.location=/root/tools/ca_temp/client.truststore.jks
   ssl.truststore.password=hello123
   ```