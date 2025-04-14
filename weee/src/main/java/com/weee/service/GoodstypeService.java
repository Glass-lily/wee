package com.weee.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weee.entity.Goodstype;

/**
 * (Goodstype)表服务接口
 *
 * @author makejava
 * @since 2025-04-14 11:57:22
 */
public interface GoodstypeService extends IService<Goodstype> {
    IPage<Goodstype> pageS(Page<Goodstype> page, Wrapper<Goodstype> wrapper);


}
