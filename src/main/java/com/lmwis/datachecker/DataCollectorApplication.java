package com.lmwis.datachecker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2021/12/16 6:41 下午
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("com.lmwis.datachecker.mapper")
public class DataCollectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataCollectorApplication.class,args);
    }
}
