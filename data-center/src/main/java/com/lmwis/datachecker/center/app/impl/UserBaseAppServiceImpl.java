package com.lmwis.datachecker.center.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.lang.componment.StringIdGenerator;
import com.fehead.lang.error.BusinessException;
import com.fehead.lang.error.EmBusinessError;
import com.fehead.lang.util.LongUtil;
import com.lmwis.datachecker.center.app.UserBaseAppService;
import com.lmwis.datachecker.center.dao.UserBaseDO;
import com.lmwis.datachecker.center.dao.mapper.UserBaseMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/17 3:42 下午
 * @Version: 1.0
 */
@Service
@AllArgsConstructor
public class UserBaseAppServiceImpl implements UserBaseAppService {

    final UserBaseMapper userBaseMapper;

    final StringIdGenerator stringIdGenerator;

    @Override
    public UserBaseDO getUserBaseById(long id) {
        if (LongUtil.nullOrZero(id)){
            return null;
        }
        return userBaseMapper.selectById(id);
    }

    @Override
    public UserBaseDO getUserBaseByToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        QueryWrapper<UserBaseDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);
        return userBaseMapper.selectOne(queryWrapper);
    }

    @Override
    public UserBaseDO getUserBaseByName(String username) throws BusinessException {
        if (StringUtils.isBlank(username)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        QueryWrapper<UserBaseDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        return userBaseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean newUser(String username) throws BusinessException {
        UserBaseDO userBaseDO = this.getUserBaseByName(username);
        if (userBaseDO != null) {
            throw new BusinessException(EmBusinessError.USER_ALREADY_EXIST);
        }
        UserBaseDO newUser = new UserBaseDO();
        newUser.setUsername(username);
        newUser.setToken(stringIdGenerator.generateId());
        Date now = new Date();
        newUser.setGmtCreate(now);
        newUser.setGmtModified(now);
        return userBaseMapper.insert(newUser)>0;
    }
}
