package com.lmwis.datachecker.init;

import io.netty.handler.codec.http.HttpMethod;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/26 8:20 下午
 * @Version: 1.0
 */
public class BrowserProxyTest {

    @Test
    public void proxyTest() {
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start(8810);
        int ip = proxy.getPort();
        System.out.println(ip);

        proxy.addRequestFilter((request, contents, messageInfo) -> {
            if (request.getMethod().equals(HttpMethod.POST)) {
                System.out.println(request.getUri() + " ######### " + contents.getTextContents());
            }
            System.out.println(request.getUri() + " --->> " + request.headers().get("Cookie"));
            return null;
        });

// create a new HAR with label
        proxy.newHar("bmp");
// enable more detailed HAR capture, if desired (see CaptureType for the complete list)
 proxy.enableHarCaptureTypes(
                        CaptureType.REQUEST_HEADERS, CaptureType.REQUEST_CONTENT, CaptureType.REQUEST_BINARY_CONTENT, CaptureType.REQUEST_COOKIES, CaptureType.RESPONSE_HEADERS, CaptureType.RESPONSE_CONTENT, CaptureType.RESPONSE_BINARY_CONTENT, CaptureType.RESPONSE_COOKIES);

// 手动输入验证码，然后继续爬取过程
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.next();
            if (cmd.equals("quit")) {
                break;
            }
        }

        try {
            Har har = proxy.getHar();
            File harFile = new File("/Users/lmwis/Desktop/bmp.har");
            har.writeTo(harFile);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        proxy.stop();
    }
}
