package com.yicj.study.config.security;

import com.yicj.study.config.model.JwtUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("userDetailsService")
public class MemberServiceImpl implements UserDetailsService {

    private static final PasswordEncoder BCRYPT = new BCryptPasswordEncoder() ;
    //private static final PasswordEncoder BCRYPT = NoOpPasswordEncoder.getInstance() ;

    @Value("${spring.security.user.name}")
    private String hardcodeUser ;

    @Value("${spring.security.user.password}")
    private String password ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 对密码进行加密
        String hardcodePassword = BCRYPT.encode(password) ;
        if (!username.equals(hardcodeUser)){
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username)) ;
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER") ;
        List<SimpleGrantedAuthority> authorities = Arrays.asList(authority);
        return new JwtUser(hardcodeUser, hardcodePassword, authorities);
    }
}
