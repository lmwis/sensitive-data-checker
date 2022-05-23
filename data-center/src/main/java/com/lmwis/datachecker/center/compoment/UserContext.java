package com.lmwis.datachecker.center.compoment;

import com.lmwis.datachecker.center.dao.UserBaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/3 8:06 下午
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class UserContext {

    final private String token;

    final private long uid;

    final private UserBaseDO userBaseDO;


}
