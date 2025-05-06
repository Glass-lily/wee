package com.weee.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.weee.entity.Text;
import com.weee.entity.TextRes;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


public interface TextMapper extends BaseMapper<Text> {
    IPage<TextRes> pageS(Page<Text> page, @Param(Constants.WRAPPER) Wrapper wrapper);


}
