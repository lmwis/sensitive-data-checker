package com.lmwis.datachecker.computer.service.convert;

import com.lmwis.datachecker.computer.dao.ResponseInfoDO;
import com.lmwis.datachecker.computer.pojo.HttpResponseInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/4/12 11:31 下午
 * @Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface HttpResponseConvert {
    HttpResponseConvert CONVERT = Mappers.getMapper(HttpResponseConvert.class);

    @Mappings({
            @Mapping(target = "headers"
                    ,expression = "java(com.lmwis.datachecker.proxy.utils.GsonUtil.toJson(responseInfo.getHeaders()))"),
    })
    ResponseInfoDO toResponseInfoDO(HttpResponseInfo responseInfo);

}
