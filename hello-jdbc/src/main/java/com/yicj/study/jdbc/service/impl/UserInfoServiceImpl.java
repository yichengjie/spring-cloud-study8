package com.yicj.study.jdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.study.jdbc.repository.entity.UserInfo;
import com.yicj.study.jdbc.repository.mapper.UserInfoMapper;
import com.yicj.study.jdbc.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public UserInfo findById(String id) {

        int a = 1/0 ;

        return null;
    }
}
