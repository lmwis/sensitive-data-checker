package com.lmwis.datachecker.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/30 5:28 下午
 * @Version: 1.0
 */

@SpringBootApplication
@MapperScan("com.lmwis.datachecker.center.dao.*")
public class DataCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataCenterApplication.class,args);
    }
}
