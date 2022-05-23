package com.lmwis.datachecker.center.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/23 2:02 下午
 * @Version: 1.0
 */
@Data
@ToString
public class UserLoginDTO {
    String username;
    String token;
}
