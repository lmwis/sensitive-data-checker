package com.lmwis.datachecker.center.convert;

import com.lmwis.datachecker.center.dao.UsageEventDO;
import com.lmwis.datachecker.center.pojo.UsageEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/21 6:44 下午
 * @Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface UsageEventConvert {

    UsageEventConvert CONVERT = Mappers.getMapper(UsageEventConvert.class);

    @Mappings({
            @Mapping(target = "timeStamp", expression = "java(new java.util.Date(usageEventDTO.getTimeStamp()))")
    })
    UsageEventDO convertToDO(UsageEventDTO usageEventDTO);
}
