package com.yicj.study.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yicj.study.BaseJunitTest;
import com.yicj.study.jdbc.repository.entity.UserInfo;
import com.yicj.study.jdbc.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Slf4j
public class UserInfoServiceTest extends BaseJunitTest {

    @Autowired
    private UserInfoService userInfoService ;


    @Autowired
    private JdbcTemplate jdbcTemplate ;

    @Test
    public void createTable(){
        // 1、建表 DDL
        String createUser = "create table user_info(" +
                "id integer primary key autoincrement," +
                "username varchar(20)," +
                "address varchar(20) " +
                ")";
        jdbcTemplate.update(createUser);
    }

    @Test
    public void insert(){
        userInfoService.save(new UserInfo("张三", "北京")) ;
        userInfoService.save(new UserInfo("李四", "天津")) ;
        userInfoService.save(new UserInfo("王五", "河北")) ;
    }


    @Test
    public void findById(){
        Integer id = 1;
        UserInfo user = userInfoService.findById(id);
        log.info("user info : {}", user);
    }

    @Test
    public void listAll(){
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>() ;
        List<UserInfo> list = userInfoService.list(wrapper);
        log.info("list size : {}", list.size());
        list.forEach(item -> log.info("===> {}", item));
    }
}
