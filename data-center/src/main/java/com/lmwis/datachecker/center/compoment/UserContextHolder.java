package com.lmwis.datachecker.center.compoment;

import com.fehead.lang.util.GsonUtil;
import com.lmwis.datachecker.center.app.UserBaseAppService;
import com.lmwis.datachecker.center.dao.UserBaseDO;
import com.lmwis.datachecker.common.perperties.CommonConfigProperties;
import com.lmwis.datachecker.common.error.BusinessErrorEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/3 8:06 下午
 * @Version: 1.0
 */
@Component
@Slf4j
public class UserContextHolder {

    @Getter
    UserContext userContext;

    final UserBaseAppService userBaseAppService;

    final CommonConfigProperties commonConfigProperties;

    UserContextHolder (UserBaseAppService userBaseAppService, CommonConfigProperties commonConfigProperties){
        this.userBaseAppService = userBaseAppService;
        this.commonConfigProperties = commonConfigProperties;
        init();
    }
    private void init(){
        if (commonConfigProperties.getUser().isUnTokenMode()){
            // 默认user
            this.userContext = new UserContext("11111",2,null);
            return;
        }
        String token = commonConfigProperties.getUser().getToken();
        if (StringUtils.isBlank(token)){
            throw new IllegalArgumentException(BusinessErrorEnum.USER_TOKEN_PARAM_INVALID.getErrorMsg());
        }
        UserBaseDO userBaseDO = userBaseAppService.getUserBaseByToken(token);
        if (userBaseDO == null){
            throw new IllegalArgumentException(BusinessErrorEnum.USER_TOKEN_PARAM_INVALID.getErrorMsg());
        }
        this.userContext = new UserContext(token, userBaseDO.getId(),userBaseDO);
        log.info("[UserContextHolder.init] success load user:{}", GsonUtil.toString(this.userContext));
    }
    public boolean verify(String token){
        return StringUtils.equals(token,this.userContext.getToken());
    }

    public Long getCurrentUid(){
        return userContext.getUid();
    }
}
