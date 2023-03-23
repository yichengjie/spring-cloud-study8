package com.yicj.study.mvc.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@ConfigurationProperties("custom.user")
public class UserInfoProperties implements Serializable {

  private String username ;

  private String address ;

}
