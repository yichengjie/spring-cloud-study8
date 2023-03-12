#### SpringBoot服务提供者开启gizp压缩
1. yaml中添加压缩配置
   ```yaml
   server:
     port: 7070
     compression:
       enabled: true
   ```
#### feign 配置
1. 针对单个feign接口进行配置
    ```yaml
    feign:
      client:
        config:
          # feignName 注意这里与contextId一致，不能写成name（FeignClientFactoryBean#configureFeign）
          # 不能写成 client-b，否则不生效
          helloFeignClient: # contextId
            connectTimeout: 50000 # 连接超时时间
            readTimeout: 50000 # 读超时时间
            loggerLevel: full #配置Feign的日志级别
          #default:
            # 其他默认配置
    ```
2. feign 全局默认配置
    ```yaml
    feign:
      client:
        config:
          default:
            connectTimeout: 50000 # 连接超时时间
            readTimeout: 50000 # 读超时时间
            loggerLevel: full #配置Feign的日志级别
    ```
3. feign开启gzip支持
    ```yaml
    feign:
      compression:
        request:
          enabled: true
          mime-types: "text/xml, application/xml, application/json"
          min-request-size: 2048
        response:
          enabled: true # 配置相应GZIP压缩
    ```
#### 开启gzip支持后接口调用处理方式一
1. feign接口使用ResponseEntity<byte []>接收数据
    ```java
    @FeignClient(contextId = "testFeignClient", 
            name = "client-a", configuration = FeignConfig.class)
    public interface TestFeignClient {
        @GetMapping(value = "/userInfo")
        ResponseEntity<byte []> userInfoCompress(
                @RequestParam("username") String username, @RequestParam("address") String address) ;
    }
    ```
2. 编写单元测试（注意需要对byte[] 数组使用Gzip解压）
    ```java
    @Slf4j
    public class TestFeignClientTest extends BaseJunitTest {
        @Autowired
        private TestFeignClient testFeignClient ;
        @Test
        public void userInfoCompress() throws IOException {
            String username = "张三" ;
            String address = "北京" ;
            ResponseEntity<byte[]> responseEntity = testFeignClient.userInfoCompress(username, address);
            byte[] compressed = responseEntity.getBody();
            String decompressValue = GzipUtils.decompress(compressed);
            log.info("value : {}", decompressValue);
        }
    }
    ```
3. 编写gzip解压缩工具类
    ```java
    public final class GzipUtils {
        public static String decompress(byte [] compressed) throws IOException {
            final StringBuilder output = new StringBuilder() ;
            try(GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressed));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8))){
                String line ;
                while ((line = bufferedReader.readLine()) != null){
                    output.append(line) ;
                }
                return output.toString() ;
            }
        }
    }
    ```
#### 开启gzip支持后接口调用处理方式二
1. 编写Decoder （内部使用方式一的Gzip解压缩工具类GzipUtils）
    ```java
    public class FeignResponseDecoder implements Decoder {
        private final Decoder delegate;
        public FeignResponseDecoder(Decoder delegate) {
            Objects.requireNonNull(delegate, "Decoder must not be null. ");
            this.delegate = delegate;
        }
        @Override
        public Object decode(Response response, Type type) throws IOException {
            Collection<String> values = response.headers().get(HttpEncoding.CONTENT_ENCODING_HEADER);
            if (Objects.nonNull(values) && !values.isEmpty() && values.contains(HttpEncoding.GZIP_ENCODING)) {
                byte[] compressed = Util.toByteArray(response.body().asInputStream());
                if ((compressed == null) || (compressed.length == 0)) {
                    return delegate.decode(response, type);
                }
                //decompression part
                //after decompress we are delegating the decompressed response to default
                //decoder
                if (isCompressed(compressed)) {
                    String decompressValue = GzipUtils.decompress(compressed);
                    Response decompressedResponse = response.toBuilder().body(decompressValue.getBytes()).build();
                    return delegate.decode(decompressedResponse, type);
                } else {
                    return delegate.decode(response, type);
                }
            } else {
                return delegate.decode(response, type);
            }
        }
        private static boolean isCompressed(final byte[] compressed) {
            return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
        }
    }
    ```
2. 将Decoder加入到Spring容器管理
   ```java
   @Configuration
   public class AppConfig{
      @Bean
      public Decoder GZIPResponseDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
         Decoder decoder = new FeignResponseDecoder(new SpringDecoder(messageConverters));
         return decoder;
      }
   }
   ```
3. feign接口使用普通java对象接收数据
   ```java
   @FeignClient(contextId = "testFeignClient", 
           name = "client-a", configuration = FeignConfig.class)
   public interface TestFeignClient {
       @GetMapping("/userInfo")
       UserInfoVO userInfo(@RequestParam("username") String username, @RequestParam("address") String address) ;
   }
   ```
4. 编写单元测试
   ```java
   @Slf4j
   public class TestFeignClientTest extends BaseJunitTest {
       @Autowired
       private TestFeignClient testFeignClient ;
       
       @Test
       public void userInfo(){
           String username = "张三" ;
           String address = "北京" ;
           UserInfoVO userInfo = testFeignClient.userInfo(username, address);
           log.info("user info : {}", userInfo);
       }
   }
   ```