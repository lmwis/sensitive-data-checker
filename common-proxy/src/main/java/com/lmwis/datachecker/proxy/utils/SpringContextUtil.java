package com.lmwis.datachecker.proxy.utils;


import org.springframework.context.ApplicationContext;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/12 10:21 下午
 * @Version: 1.0
 */
public class SpringContextUtil {

    static ApplicationContext context;

    public static ApplicationContext getApplicationContext(){
        return context;
    }

    public static void setApplicationContext(ApplicationContext applicationContext){
        context = applicationContext;
    }
}
