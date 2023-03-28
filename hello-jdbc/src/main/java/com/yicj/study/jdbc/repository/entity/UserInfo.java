package com.yicj.study.jdbc.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("user_info")
@NoArgsConstructor
public class UserInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id ;

    private String username ;

    private String address ;

    public UserInfo(String username, String address){
        this.username = username ;
        this.address = address ;
    }
}
