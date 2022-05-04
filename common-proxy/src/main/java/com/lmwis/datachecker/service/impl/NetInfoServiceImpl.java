package com.lmwis.datachecker.service.impl;

import com.lmwis.datachecker.dao.RequestInfoDO;
import com.lmwis.datachecker.dao.ResponseInfoDO;
import com.lmwis.datachecker.dao.mapper.RequestInfoDOMapper;
import com.lmwis.datachecker.dao.mapper.ResponseInfoDOMapper;
import com.lmwis.datachecker.pojo.HttpRequestInfo;
import com.lmwis.datachecker.pojo.HttpResponseInfo;
import com.lmwis.datachecker.service.NetInfoService;
import com.lmwis.datachecker.service.convert.HttpRequestConvert;
import com.lmwis.datachecker.service.convert.HttpResponseConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/12 10:00 下午
 * @Version: 1.0
 */

@Service
@RequiredArgsConstructor
public class NetInfoServiceImpl implements NetInfoService {

    final RequestInfoDOMapper requestInfoDOMapper;

    final ResponseInfoDOMapper responseInfoDOMapper;

    @Override
    public Boolean saveHttpInfo(HttpRequestInfo requestInfo, HttpResponseInfo responseInfo) {

        if (requestInfo == null || responseInfo == null){
            return false;
        }

        RequestInfoDO requestInfoDO = HttpRequestConvert.CONVERT.toRequestInfoDO(requestInfo);
        int insert = requestInfoDOMapper.insert(requestInfoDO);
        ResponseInfoDO responseInfoDO = HttpResponseConvert.CONVERT.toResponseInfoDO(responseInfo);
        responseInfoDO.setRequestInfoId(requestInfoDO.getId());
        int insert1 = responseInfoDOMapper.insert(responseInfoDO);

        return insert > 0 | insert1>0;
//        return true;
    }
}
