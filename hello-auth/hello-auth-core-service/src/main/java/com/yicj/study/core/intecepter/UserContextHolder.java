package com.yicj.study.core.intecepter;

import com.yicj.study.core.vo.User;

public class UserContextHolder {
    private static ThreadLocal<User> threadLocal = new ThreadLocal<>() ;

    public static void set(User user){
        threadLocal.set(user);
    }

    public static User currentUser(){
        return threadLocal.get() ;
    }

    public static void shutdown(){
        threadLocal.remove();
    }
}
