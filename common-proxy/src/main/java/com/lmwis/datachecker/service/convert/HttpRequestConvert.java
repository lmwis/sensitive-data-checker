package com.lmwis.datachecker.service.convert;

import com.lmwis.datachecker.dao.RequestInfoDO;
import com.lmwis.datachecker.pojo.HttpRequestInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/12 10:05 下午
 * @Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface HttpRequestConvert {

    HttpRequestConvert CONVERT = Mappers.getMapper(HttpRequestConvert.class);

    @Mappings({
            @Mapping(target = "headers",expression = "java(com.lmwis.datachecker.utils.GsonUtil.toJson(requestInfo.getHeaders()))")
    })
    RequestInfoDO toRequestInfoDO(HttpRequestInfo requestInfo);


//
//    ResponseInfoDO toResponseInfoDO(HttpResponseInfo responseInfo);

}
