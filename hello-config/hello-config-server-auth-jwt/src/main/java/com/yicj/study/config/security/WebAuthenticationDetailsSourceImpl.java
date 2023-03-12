package com.yicj.study.config.security;

import com.google.gson.Gson;
import com.yicj.study.config.model.JwtAuthenticationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Component
public class WebAuthenticationDetailsSourceImpl implements AuthenticationDetailsSource<HttpServletRequest, JwtAuthenticationRequest> {

    @Override
    public JwtAuthenticationRequest buildDetails(HttpServletRequest request) {
        Gson gson = new Gson() ;
        StringBuilder buffer = new StringBuilder() ;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))){
            String output ;
            while ((output = br.readLine()) != null){
                buffer.append(output) ;
            }
            return gson.fromJson(buffer.toString(), JwtAuthenticationRequest.class);
        }catch (IOException e){
            log.error("解析请求异常"); ;
        }
        return null;
    }
}
