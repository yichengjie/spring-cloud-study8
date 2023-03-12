package com.yicj.study.config.controller;

import com.yicj.study.config.model.JwtAuthenticationRequest;
import com.yicj.study.config.model.JwtAuthenticationResponse;
import com.yicj.study.config.model.JwtUser;
import com.yicj.study.config.security.JwtTokenUtil;
import com.yicj.study.config.security.MemberServiceImpl;
import com.yicj.study.config.security.WebAuthenticationDetailsSourceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationRestController {

    @Autowired
    private AuthenticationManager authenticationManager ;

    @Autowired
    private JwtTokenUtil jwtTokenUtil ;

    @Autowired
    private MemberServiceImpl userDetailsService ;

    @Autowired
    private WebAuthenticationDetailsSourceImpl webAuthenticationDetailsSource ;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthenticationToken(HttpServletRequest request){

        JwtAuthenticationRequest jwtAuthenticationRequest = webAuthenticationDetailsSource.buildDetails(request) ;
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                jwtAuthenticationRequest.getUsername(), jwtAuthenticationRequest.getPassword()
        ) ;
        authToken.setDetails(jwtAuthenticationRequest);
        Authentication authenticate = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        JwtUser userDetails = (JwtUser)userDetailsService.loadUserByUsername(jwtAuthenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token)) ;
    }

}
