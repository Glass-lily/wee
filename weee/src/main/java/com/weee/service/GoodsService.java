package com.weee.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weee.entity.Goods;

/**
 * (Goods)表服务接口
 *
 * @author makejava
 * @since 2025-04-14 12:20:47
 */
public interface GoodsService extends IService<Goods> {
    IPage<Goods> pageS(Page<Goods> page, Wrapper<Goods> wrapper);


}
