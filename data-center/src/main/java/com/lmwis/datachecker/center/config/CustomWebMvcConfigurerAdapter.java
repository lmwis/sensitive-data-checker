package com.lmwis.datachecker.center.config;

import com.lmwis.datachecker.common.perperties.CommonConfigProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/20 3:24 下午
 * @Version: 1.0
 */
@Configuration
@AllArgsConstructor
public class CustomWebMvcConfigurerAdapter implements WebMvcConfigurer {

    final CommonConfigProperties commonConfigProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //指向外部目录
        registry.addResourceHandler("file//**").addResourceLocations("file:"+commonConfigProperties.getDiskFilePath());

    }
}
