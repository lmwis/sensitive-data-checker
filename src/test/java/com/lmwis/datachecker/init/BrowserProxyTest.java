package com.lmwis.datachecker.init;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.junit.Test;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/26 8:20 下午
 * @Version: 1.0
 */
public class BrowserProxyTest {

    @Test
    public void proxyTest(){
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start(0);
    }
}
