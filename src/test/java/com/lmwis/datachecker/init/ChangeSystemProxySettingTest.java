package com.lmwis.datachecker.init;

import org.junit.Test;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/1/7 2:18 下午
 * @Version: 1.0
 */
public class ChangeSystemProxySettingTest {

    @Test
    public void doChange(){
        System.out.println(11);
        //在你发起Http请求之前设置一下属性
        System.setProperty( "http.proxyHost" ,  "127.0.0.1" );
        System.setProperty( "http.proxyPort" ,  "8080" );
    }
}
