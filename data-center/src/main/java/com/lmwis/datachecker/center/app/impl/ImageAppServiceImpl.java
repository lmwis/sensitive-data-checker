package com.lmwis.datachecker.center.app.impl;

import com.fehead.lang.componment.StringIdGenerator;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.lmwis.datachecker.center.app.ImageAppService;
import com.lmwis.datachecker.common.perperties.CommonConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/20 12:37 下午
 * @Version: 1.0
 */
@Service
@Slf4j
public class ImageAppServiceImpl implements ImageAppService {

    final CommonConfigProperties commonConfigProperties;

    final Environment environment;

    final StringIdGenerator stringIdGenerator;

    final static String SERVER_PORT = "server.port";
    //获取图片链接
    final String fileUrlPath = "/file/";

    final String port;
    final String host ;
    final String diskPath;

    final static String PNG_SUFFIX = ".png";

    public ImageAppServiceImpl(CommonConfigProperties commonConfigProperties, Environment environment, StringIdGenerator stringIdGenerator) {
        this.commonConfigProperties = commonConfigProperties;
        this.environment = environment;
        this.stringIdGenerator = stringIdGenerator;

        this.port = environment.getProperty(SERVER_PORT);
        this.host = commonConfigProperties.getHost();
        this.diskPath = commonConfigProperties.getDiskFilePath();
    }

    @Override
    public String saveFile(MultipartFile file) throws BusinessException {
        // 获取文件名
        String fileName = file.getOriginalFilename();
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

    /**
     * 固定保存为png后缀
     * @param file
     * @return
     * @throws BusinessException
     */
    @Override
    public String saveStringFile(String file) throws BusinessException {
        if (StringUtils.isEmpty(file)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 获取文件名
        String fileName = stringIdGenerator.generateId()+PNG_SUFFIX;
        String fileUrl = host + ":" + port + fileUrlPath + fileName;
        byte[] decode = Base64.getDecoder().decode(file);

        Path path = Paths.get(diskPath+fileName);
        try {
            Files.write(path, decode);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return fileUrl;
    }
}
