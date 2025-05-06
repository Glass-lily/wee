package com.weee.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weee.entity.Text;
import com.weee.entity.TextRes;

import java.util.List;

/**
 * (Goods)表服务接口
 *
 * @author makejava
 * @since 2025-04-14 12:20:47
 */
public interface TextService extends IService<Text> {
    IPage<TextRes> pageS(Page<Text> page, Wrapper<Text> wrapper);

}
