package com.yicj.study.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;


@Slf4j
@SpringBootTest(classes = JdbcApplication.class)
@RunWith(SpringRunner.class)
public class JdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate ;

    @Test
    public void createTable(){
        // 1、建表 DDL
        String createUser = "create table user(" +
                "id integer primary key autoincrement," +
                "name varchar(20)," +
                "age integer" +
                ")";
        jdbcTemplate.update(createUser);
    }

    @Test
    public void insert(){
        // 2、插入数据
        String insertUserData = "insert into user(name,age) values ('张三',18),('李四',20)";
        jdbcTemplate.update(insertUserData);
    }

    @Test
    public void query(){
        // 3、查询语句
        String selectUserData = "select * from user";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(selectUserData);
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        }
    }

}
