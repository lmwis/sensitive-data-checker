package com.lmwis.datachecker.center.app;

import com.lmwis.datachecker.center.dao.ProcessBaseDO;
import com.lmwis.datachecker.center.dao.ProcessBaseDTO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/15 1:20 下午
 * @Version: 1.0
 */
public interface ProcessBaseAppService {

    ProcessBaseDO insertProcessBase(ProcessBaseDTO processBaseDTO);

    ProcessBaseDO selectByProcessName(String name) ;

    ProcessBaseDO selectById(long id) ;

}
