package com.yicj.study.feign.remote.feign;

import com.yicj.study.feign.BaseJunitTest;
import com.yicj.study.feign.model.UserInfoVO;
import com.yicj.study.feign.utils.GzipUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.io.IOException;

@Slf4j
public class TestFeignClientTest extends BaseJunitTest {

    @Autowired
    private TestFeignClient testFeignClient ;

    @Test
    public void add(){
        Integer a = 1 ;
        Integer b = 2 ;
        Integer result = testFeignClient.add(a, b);
        log.info("result : {}", result);
    }

    @Test
    public void addCompress() throws IOException {
        Integer a = 1 ;
        Integer b = 2 ;
        ResponseEntity<byte[]> responseEntity = testFeignClient.addCompress(a, b);
        byte[] body = responseEntity.getBody();
        //String value = new String(body, StandardCharsets.UTF_8) ;
        String value = GzipUtils.decompress(body);
        log.info("value : {}", value);
    }

    @Test
    public void userInfo(){
        String username = "张三" ;
        String address = "北京" ;
        UserInfoVO userInfo = testFeignClient.userInfo(username, address);
        log.info("user info : {}", userInfo);
    }

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
