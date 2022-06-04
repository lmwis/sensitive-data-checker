package com.lmwis.datachecker.center.app;

import com.fehead.lang.error.BusinessException;
import com.lmwis.datachecker.center.dao.AppBaseDO;
import com.lmwis.datachecker.center.pojo.BatchInitAppBaseDTO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/19 1:52 下午
 * @Version: 1.0
 */
public interface AppBaseAppService {

    boolean batchInitAppBase(BatchInitAppBaseDTO batchInitAppBaseDTO) throws BusinessException;

    AppBaseDO selectAppBaseDOByPackageName(String packageName);

    AppBaseDO selectById(long id);

    int selectAllCount();
}
