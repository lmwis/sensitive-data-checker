package com.lmwis.datachecker.computer.net.start.setting;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/2/1 2:17 下午
 * @Version: 1.0
 */
public class JvmHookSetting {
    public static void registerShutdownHook(){

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            System.out.println("set off proxy");
            try {
                ProxySetCommand.setOffProxy();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));
    }
}


