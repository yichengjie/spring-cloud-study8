package com.yicj.study.jdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yicj.study.jdbc.repository.entity.UserInfo;

public interface UserInfoService extends IService<UserInfo> {

    UserInfo findById(Integer id) ;

    int updateUserNameById(String username, Integer id) ;
}
