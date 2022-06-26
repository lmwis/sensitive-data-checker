package com.lmwis.datachecker.computer.init;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/18 1:15 下午
 * @Version: 1.0
 */
public class ResourceRenderer {
    public static InputStream resourceLoader(String fileFullPath) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        try {
            return resourceLoader.getResource(fileFullPath).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
