package com.yicj.study.core.intecepter;

import com.alibaba.fastjson.JSON;
import com.yicj.study.core.util.UserPermissionUtil;
import com.yicj.study.core.vo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = getUser(request) ;
        UserPermissionUtil.permission(user);
        if (!UserPermissionUtil.verify(user, request)){
            response.setHeader("Content-Type", "application/json");
            String jsonStr = JSON.toJSONString("no permission access service, please check");
            response.getWriter().write(jsonStr);
            response.getWriter().flush();
            response.getWriter().close();
            throw new PermissionException("no permission access service, please check") ;
        }
        UserContextHolder.set(user);
        return true ;
    }

    private User getUser(HttpServletRequest request) {
        String userId = request.getHeader("x-user-id");
        String userName = request.getHeader("x-user-name");
        User user = new User() ;
        user.setUserId(userId);
        user.setUserName(userName);
        return user ;
    }

    static class PermissionException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        public PermissionException(String msg) {
            super(msg);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.shutdown();
    }
}
