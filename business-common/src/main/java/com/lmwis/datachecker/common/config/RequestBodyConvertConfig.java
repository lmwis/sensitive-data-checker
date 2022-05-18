package com.lmwis.datachecker.common.config;

import com.fehead.lang.util.GsonUtil;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/18 1:24 下午
 * @Version: 1.0
 */
@Configuration
public class RequestBodyConvertConfig  {

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter< ? >> converters) {
//        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
//        converters.add(gsonHttpMessageConverter);
//    }
@Bean
public HttpMessageConverters customConverters() {
    // 创建 convert 消息转换对象
    Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    // 创建与配置 Gson 对象
//    Gson gson = new GsonBuilder()
//            .setDateFormat("yyyy-MM-dd HH:mm:ss")
//            .excludeFieldsWithoutExposeAnnotation()
//            .disableHtmlEscaping()
//            .create();
    gsonHttpMessageConverter.setGson(GsonUtil.gson);
    // 将convert 添加到 converters
    messageConverters.add(gsonHttpMessageConverter);
    return new HttpMessageConverters(true, messageConverters);
}
}
