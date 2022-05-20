package com.lmwis.datachecker.center.app.impl;

import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.lmwis.datachecker.center.app.ImageAppService;
import com.lmwis.datachecker.common.perperties.CommonConfigProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/20 12:37 下午
 * @Version: 1.0
 */
@Service
@Slf4j
@AllArgsConstructor
public class ImageAppServiceImpl implements ImageAppService {

    final CommonConfigProperties commonConfigProperties;

    final Environment environment;

    final static String SERVER_PORT = "server.port";
    //获取图片链接
    final String fileUrlPath = "/file/";

    @Override
    public String saveFile(MultipartFile file) throws BusinessException {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        String port = environment.getProperty(SERVER_PORT);
        String host = commonConfigProperties.getHost();
        String diskPath = commonConfigProperties.getDiskFilePath();
        String filUrl = host + ":" + port + fileUrlPath + fileName;

        //创建文件路径
        File dest = new File(diskPath + fileName);

        // 重名文件处理
        if (dest.exists()){
            // TODO: 重名文件处理逻辑
        }
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            // 目录不存在则创建目录
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,e.getMessage());
        }
        return filUrl;
    }
}
