package com.weee.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.weee.entity.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;

@Mapper
public interface StorageMapper extends BaseMapper<Storage> {
   IPage pageS(Page<com.weee.entity.Storage> page, @Param(Constants.WRAPPER) Wrapper wrapper);

//    IPage pageS(Page<com.weee.entity.Storage> page, Wrapper wrapper);
}
