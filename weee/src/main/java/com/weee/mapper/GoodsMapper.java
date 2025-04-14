package com.weee.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.weee.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;

public interface GoodsMapper extends BaseMapper<Goods> {
    IPage pageS(Page<Goods> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
