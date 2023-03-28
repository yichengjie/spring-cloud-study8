package com.yicj.study.jdbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
    public UserInfo findById(Integer id) {

        return this.baseMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateByNameById(String username, Integer id) {
        LambdaUpdateWrapper<UserInfo> wrapper = new LambdaUpdateWrapper<>() ;
        wrapper.set(UserInfo::getUsername, username) ;
        wrapper.eq(UserInfo::getId, id) ;
        int count = this.baseMapper.update(null, wrapper);
        // 抛出异常
        int a = 1/0 ;
        return count ;
    }

}
