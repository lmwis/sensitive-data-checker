package com.lmwis.datachecker.center.app;

import com.fehead.lang.error.BusinessException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/20 12:26 下午
 * @Version: 1.0
 */
public interface ImageAppService {

    /**
     * 保存文件到服务器并且将映射写到数据库
     * 无文件大小限制
     * @param file
     * @return
     */
    String saveFile(MultipartFile file) throws BusinessException;
}
