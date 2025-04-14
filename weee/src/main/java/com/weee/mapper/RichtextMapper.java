package com.weee.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weee.entity.Richtext;
import org.apache.ibatis.annotations.Param;


public interface RichtextMapper extends BaseMapper<Richtext> {
    IPage pageS(Page<Richtext> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
