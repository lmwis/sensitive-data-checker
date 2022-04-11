package com.lmwis.datachecker.pratice.setting;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/2/1 2:17 下午
 * @Version: 1.0
 */
@Slf4j
public class JvmHookSetting {
    public static void registerShutdownHook(){

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            log.info("[registerShutdownHook] set off proxy");
            try {
                ProxySetCommand.setOffProxy();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}


