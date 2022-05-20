package com.lmwis.datachecker.common.perperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/17 2:54 下午
 * @Version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "app")
public class CommonConfigProperties {

    private UserConfigProperties user = new UserConfigProperties();

    private List<String> aclList ;

    private String host;

    private String diskFilePath;
}
