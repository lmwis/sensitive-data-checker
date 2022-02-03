package com.lmwis.datachecker.test;

import org.junit.Test;

import java.net.URL;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/2/3 2:44 下午
 * @Version: 1.0
 */
public class ClasspathTest {

    @Test
    public void getClasspath(){
        URL resource = Thread.currentThread().getContextClassLoader().getResource("");
        System.out.println(resource.getPath());
    }
}
