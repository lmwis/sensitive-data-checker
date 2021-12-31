package com.lmwis.datachecker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 6:41 下午
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("com.lmwis.datachecker.mapper")
@EnableScheduling
public class DataCollectorApplication {
    static{
        System.setProperty("java.awt.headless", "false");
    }
    public static void main(String[] args) {
        
        SpringApplication.run(DataCollectorApplication.class,args);
    }
}
