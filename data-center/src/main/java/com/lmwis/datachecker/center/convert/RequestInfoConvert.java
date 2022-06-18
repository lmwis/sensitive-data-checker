package com.lmwis.datachecker.center.convert;

import com.lmwis.datachecker.center.dao.RequestInfoDO;
import com.lmwis.datachecker.center.pojo.RequestInfoDTO;
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
public interface RequestInfoConvert {

    RequestInfoConvert CONVERT = Mappers.getMapper(RequestInfoConvert.class);

    @Mappings({
            @Mapping(target = "headers",expression = "java(com.fehead.lang.util.GsonUtil.toString(requestInfo.getHeaders()))"),
            @Mapping(target = "gmtCreate", expression = "java(new java.util.Date(requestInfo.getGmtCreate()))"),
            @Mapping(target = "gmtModified", expression = "java(new java.util.Date(requestInfo.getGmtModified()))")
    })
    RequestInfoDO toRequestInfoDO(RequestInfoDTO requestInfo);


//    @Mappings({
//            @Mapping(target = "gmtCreate", expression = "java(new java.util.Date(requestInfoDO.getGmtCreate()))"),
//            @Mapping(target = "gmtModified", expression = "java(new java.util.Date(requestInfoDO.getGmtModified()))")
//    })
//    RequestInfoDTO convertToDTO(RequestInfoDO requestInfoDO);

}
