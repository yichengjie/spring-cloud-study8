package com.yicj.study.jdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.study.jdbc.repository.entity.UserInfo;
import com.yicj.study.jdbc.repository.mapper.UserInfoMapper;
import com.yicj.study.jdbc.service.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

}
