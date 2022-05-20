package com.lmwis.datachecker.center.controller;

import com.fehead.lang.controller.BaseController;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.response.CommonReturnType;
import com.lmwis.datachecker.center.app.ImageAppService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/20 12:20 下午
 * @Version: 1.0
 */
@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class ImageController extends BaseController {

    final ImageAppService imageAppService;

    final static long LIMIT_SIZE = 10*1024*1024;

    /**
     * 文件上传
     * 限制10M
     * @param file
     * @return
     * @throws BusinessException
     */
    @PostMapping()
    public CommonReturnType uploadImageLimit10M(MultipartFile file) throws BusinessException {

        if (file.isEmpty()) {
            throw new BusinessException(EmBusinessError.UPLOAD_FILE_EMPTY);
        }
        if (file.getSize() > LIMIT_SIZE) {
            throw new BusinessException(EmBusinessError.UPLOAD_FILE_SIZE_LIMIT);
        }

        //将链接保存到URL中
        String urlPath = imageAppService.saveFile(file);
        return CommonReturnType.create(urlPath);

    }

}
