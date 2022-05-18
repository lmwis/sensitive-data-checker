package com.lmwis.datachecker.proxy.utils;

import com.relops.snowflake.Snowflake;
import org.apache.commons.lang3.RandomUtils;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/11 10:35 下午
 * @Version: 1.0
 */
public class IdUtil {
    private static Snowflake s = new Snowflake(RandomUtils.nextInt(0, 1024));

    public static long next(){
        return s.next();
    }
}
