package com.yicj.study.config.security.filter;

import com.yicj.study.config.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends GenericFilterBean {

    @Autowired
    private UserDetailsService userDetailsService ;

    @Autowired
    private JwtTokenUtil jwtTokenUtil ;

    private final String tokenHeader = "header";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request ;
        String authToken = httpRequest.getHeader(tokenHeader);
        if (authToken!= null && SecurityContextHolder.getContext().getAuthentication() == null){
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (username != null && jwtTokenUtil.validateToken(authToken, userDetails)){
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username,null, userDetails.getAuthorities()
                ) ;
                WebAuthenticationDetails details = new WebAuthenticationDetailsSource().buildDetails(httpRequest);
                auth.setDetails(details) ;
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
    }
}
