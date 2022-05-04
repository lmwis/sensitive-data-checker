package com.lmwis.datachecker.center.compoment;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/3 8:06 下午
 * @Version: 1.0
 */
public class UserContext {
    static UserContext userContext = new UserContext();

    public static Long getCurrentUid(){
        return 2l;
    }
}
