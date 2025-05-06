package com.weee.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weee.entity.Text;
import com.weee.entity.TextRes;
import com.weee.mapper.TextMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Goods)表服务接口
 *
 * @author makejava
 * @since 2025-04-14 12:20:47
 */
public interface TextService extends IService<Text> {

    // 分页查询
    IPage<TextRes> pageS(Page<Text> page, Wrapper<Text> wrapper);

    // 根据ID查详情
    TextRes getDetailById(Long id);
}
