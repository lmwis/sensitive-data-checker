package com.lmwis.datachecker.proxy;

import com.lmwis.datachecker.proxy.utils.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/12 9:38 下午
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("com.lmwis.datachecker.computer.dao.mapper")
public class ApplicationStarter {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = SpringApplication.run(ApplicationStarter.class, args);
        SpringContextUtil.setApplicationContext(context);
        ProxyMain.starterServer(args);
    }
}
