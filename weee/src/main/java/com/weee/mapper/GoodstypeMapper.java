package com.weee.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weee.entity.Goodstype;
import org.apache.ibatis.annotations.Param;


public interface GoodstypeMapper extends BaseMapper<Goodstype> {
    IPage pageS(Page<Goodstype> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
