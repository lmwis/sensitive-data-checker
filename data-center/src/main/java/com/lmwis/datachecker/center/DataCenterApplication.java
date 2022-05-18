package com.lmwis.datachecker.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/30 5:28 下午
 * @Version: 1.0
 */

@SpringBootApplication
@ComponentScan({"com.lmwis.datachecker.*","com.fehead.lang.*"})
@MapperScan("com.lmwis.datachecker.center.dao.*")
@EnableAutoConfiguration(exclude = { JacksonAutoConfiguration.class })
public class DataCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataCenterApplication.class,args);
    }
}
