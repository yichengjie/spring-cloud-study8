package com.yicj.study.config.config;

import com.yicj.study.config.security.JwtAuthenticationEntryPoint;
import com.yicj.study.config.security.WebAuthenticationDetailsSourceImpl;
import com.yicj.study.config.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unAuthorizedHandler ;

    @Autowired
    private WebAuthenticationDetailsSourceImpl webAuthenticationDetailsSource ;

    @Bean
    @ConditionalOnMissingBean(AuthenticationManager.class)
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(
            AuthenticationManager authenticationManager
    )throws Exception{
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter() ;
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationDetailsSource(webAuthenticationDetailsSource);
        return filter ;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance() ;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public JwtAuthenticationTokenFilter authenticationTokenFilter(){
        JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter() ;
        return filter ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //.exceptionHandling().authenticationEntryPoint(unAuthorizedHandler)
                //.and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().authenticationDetailsSource(webAuthenticationDetailsSource)
                .permitAll() ;
        // 添加自定义jwt的安全过滤器
        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class) ;
        http.headers().cacheControl() ;
    }
}
