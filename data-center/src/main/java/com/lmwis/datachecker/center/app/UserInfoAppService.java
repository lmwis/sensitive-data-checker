package com.lmwis.datachecker.center.app;

import com.fehead.lang.error.BusinessException;
import com.lmwis.datachecker.center.dao.MyComputerInfo;
import com.lmwis.datachecker.center.pojo.MyComputerInfoDTO;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/14 5:54 下午
 * @Version: 1.0
 */
public interface UserInfoAppService {

    public MyComputerInfo uploadComputer(MyComputerInfoDTO myComputerInfo) throws BusinessException;
}
