package com.lmwis.datachecker.center.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lmwis.datachecker.center.app.NetInfoAppService;
import com.lmwis.datachecker.center.compoment.UserContextHolder;
import com.lmwis.datachecker.center.convert.RequestInfoConvert;
import com.lmwis.datachecker.center.convert.ResponseInfoConvert;
import com.lmwis.datachecker.center.dao.RequestInfoDO;
import com.lmwis.datachecker.center.dao.ResponseInfoDO;
import com.lmwis.datachecker.center.dao.mapper.RequestInfoDOMapper;
import com.lmwis.datachecker.center.dao.mapper.ResponseInfoDOMapper;
import com.lmwis.datachecker.center.pojo.BatchQueryNetInfoResult;
import com.lmwis.datachecker.center.pojo.BatchUploadNetInfoDTO;
import com.lmwis.datachecker.center.pojo.NetInfoResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/6/17 3:39 下午
 * @Version: 1.0
 */
@Service
@RequiredArgsConstructor
public class NetInfoAppServiceImpl implements NetInfoAppService {

    final UserContextHolder userContextHolder;

    final RequestInfoDOMapper requestInfoDOMapper;

    final ResponseInfoDOMapper responseInfoDOMapper;

    @Override
    public boolean batchUploadNetInfo(BatchUploadNetInfoDTO batchUploadNetInfoDTO) {
        if (batchUploadNetInfoDTO == null || CollectionUtils.isEmpty(batchUploadNetInfoDTO.getList())){
            return false;
        }

        batchUploadNetInfoDTO.getList().forEach(k->{
            RequestInfoDO requestInfoDO = RequestInfoConvert.CONVERT.toRequestInfoDO(k.getRequest());
            requestInfoDO.setUid(userContextHolder.getCurrentUid());

            requestInfoDOMapper.insert(requestInfoDO);

            ResponseInfoDO responseInfoDO = ResponseInfoConvert.CONVERT.toResponseInfoDO(k.getResponse());
            responseInfoDO.setRequestInfoId(requestInfoDO.getId());
            responseInfoDO.setUid(userContextHolder.getCurrentUid());

            responseInfoDOMapper.insert(responseInfoDO);
        });

        return true;

    }

    @Override
    public BatchQueryNetInfoResult batchQueryNetInfoRangTime(long startTime, long endTime) {
        QueryWrapper<RequestInfoDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid",userContextHolder.getCurrentUid());
        queryWrapper.between("gmt_create",new Date(startTime), new Date(endTime));
        queryWrapper.orderByAsc("gmt_create");
        List<NetInfoResult> list = new ArrayList<>();
        requestInfoDOMapper.selectList(queryWrapper).forEach(k->{
            QueryWrapper<ResponseInfoDO> queryResponse = new QueryWrapper<>();
            queryResponse.eq("uid",userContextHolder.getCurrentUid());
            queryResponse.eq("request_info_id",k.getId());
            ResponseInfoDO responseInfoDO = responseInfoDOMapper.selectOne(queryResponse);
            if (responseInfoDO != null){
                list.add(resolveNetInfoResult(k,responseInfoDO));
            }
        });

        return BatchQueryNetInfoResult.builder()
                .list(list)
                .count(list.size())
                .build();
    }

    @Override
    public int queryCountADay(String day) {
        QueryWrapper<RequestInfoDO> queryRequest = new QueryWrapper<>();
        queryRequest.eq("uid",userContextHolder.getCurrentUid());
        queryRequest.apply("DATE_FORMAT(gmt_create,'%Y-%m-%d') = \""+day+"\"");
        QueryWrapper<ResponseInfoDO> queryResponse = new QueryWrapper<>();
        queryResponse.eq("uid",userContextHolder.getCurrentUid());
        queryResponse.apply("DATE_FORMAT(gmt_create,'%Y-%m-%d') = \""+day+"\"");
        return requestInfoDOMapper.selectCount(queryRequest).intValue() + responseInfoDOMapper.selectCount(queryResponse).intValue();
    }

    private NetInfoResult resolveNetInfoResult(RequestInfoDO requestInfoDO , ResponseInfoDO responseInfoDO){
        return NetInfoResult.builder()
                .id(requestInfoDO.getId())
                .date(requestInfoDO.getGmtCreate().getTime())
                .method(requestInfoDO.getMethod())
                .host(requestInfoDO.getHostname())
                .url(requestInfoDO.getUrl())
                .statusCode(Integer.parseInt(responseInfoDO.getResponseCode()))
                .requestHeaders(requestInfoDO.getHeaders())
                .requestBody(requestInfoDO.getContent())
                .responseHeaders(responseInfoDO.getHeaders())
                .responseBody(responseInfoDO.getContent())
                .build();

    }
}
