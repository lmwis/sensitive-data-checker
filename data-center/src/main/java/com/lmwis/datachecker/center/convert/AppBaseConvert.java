package com.lmwis.datachecker.center.convert;

import com.lmwis.datachecker.center.dao.AppBaseDO;
import com.lmwis.datachecker.center.pojo.AppBaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description: TODO
 * @Author: lmwis
 * @Data: 2022/5/19 1:51 下午
 * @Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface AppBaseConvert {

    AppBaseConvert CONVERT = Mappers.getMapper(AppBaseConvert.class);

    AppBaseDO convertToDO(AppBaseDTO appBaseDTO);
}
