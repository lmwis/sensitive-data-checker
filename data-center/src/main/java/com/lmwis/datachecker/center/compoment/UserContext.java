package com.lmwis.datachecker.center.compoment;

import lombok.Data;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/3 8:06 下午
 * @Version: 1.0
 */
@Data
public class UserContext {

    private String token;

    private long uid;

    protected UserContext(String token, long id) {
        this.token = token;
        this.uid = id;
    }
}
