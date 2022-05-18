package com.lmwis.datachecker.center.app;

import com.fehead.lang.error.BusinessException;
import com.lmwis.datachecker.center.dao.UserBaseDO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/17 3:41 下午
 * @Version: 1.0
 */
public interface UserBaseAppService {

    UserBaseDO getUserBaseById(long id);

    UserBaseDO getUserBaseByToken(String token);

    UserBaseDO getUserBaseByName(String username) throws BusinessException;

    boolean newUser(String username) throws BusinessException;
}
