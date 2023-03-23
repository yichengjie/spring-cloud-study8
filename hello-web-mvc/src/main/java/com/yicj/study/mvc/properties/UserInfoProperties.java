package com.yicj.study.mvc.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Slf4j
@Data
@ConfigurationProperties("custom.user")
public class UserInfoProperties implements Serializable {

  private String username ;

  private String address ;


  public UserInfoProperties(){
      log.info("UserInfoProperties new() .");
  }

}
