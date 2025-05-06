package com.weee.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.weee.entity.Text;
import com.weee.entity.TextRes;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;


public interface TextMapper extends BaseMapper<Text> {
    IPage<TextRes> pageS(Page<Text> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    // 分页查询
    List<Text> listPage(@Param("name") String name, @Param("storage") String storage, @Param("goodstype") String goodstype);

    // 根据ID查详情
    Text findById(@Param("id") Long id);
    // 根据ID查询详情
    TextRes getDetailById(@Param("id") Long id);
    // 新增
    int insert(Text text);

    // 更新
    int update(Text text);

    // 删除
    int delete(@Param("id") Long id);
}
