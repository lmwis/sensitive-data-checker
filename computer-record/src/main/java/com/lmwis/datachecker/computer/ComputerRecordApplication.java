package com.lmwis.datachecker.computer;

import com.lmwis.datachecker.computer.net.start.main.ProxyServerRun;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 6:41 下午
 * @Version: 1.0
 */
@SpringBootApplication
@ComponentScan({"com.lmwis.datachecker.*","com.fehead.lang.*"})
@MapperScan("com.lmwis.datachecker.computer.dao.mapper")
@EnableScheduling
public class ComputerRecordApplication {
    static{
        System.setProperty("java.awt.headless", "false");
    }
    static final int PROXY_PORT = 9010;
    public static void main(String[] args) {

        new Thread(()->{
            try {
                ProxyServerRun.run(PROXY_PORT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        
        SpringApplication.run(ComputerRecordApplication.class,args);


    }
}
