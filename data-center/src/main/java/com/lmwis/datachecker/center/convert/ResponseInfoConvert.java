package com.lmwis.datachecker.center.convert;

import com.lmwis.datachecker.center.dao.ResponseInfoDO;
import com.lmwis.datachecker.center.pojo.ResponseInfoDTO;
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
public interface ResponseInfoConvert {
    ResponseInfoConvert CONVERT = Mappers.getMapper(ResponseInfoConvert.class);

    @Mappings({
            @Mapping(target = "headers"
                    ,expression = "java(com.fehead.lang.util.GsonUtil.toString(responseInfo.getHeaders()))"),
            @Mapping(target = "gmtCreate", expression = "java(new java.util.Date(responseInfo.getGmtCreate()))"),
            @Mapping(target = "gmtModified", expression = "java(new java.util.Date(responseInfo.getGmtModified()))")
    })
    ResponseInfoDO toResponseInfoDO(ResponseInfoDTO responseInfo);

//    @Mappings({
//            @Mapping(target = "gmtCreate", expression = "java(new java.util.Date(responseInfoDO.getGmtCreate()))"),
//            @Mapping(target = "gmtModified", expression = "java(new java.util.Date(responseInfoDO.getGmtModified()))")
//    })
//    ResponseInfoDTO convertToDTO(ResponseInfoDO responseInfoDO);

}
