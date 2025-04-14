package com.weee.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weee.entity.Richtext;

/**
 * (Richtext)表服务接口
 *
 * @author makejava
 * @since 2025-04-14 15:43:55
 */
public interface RichtextService extends IService<Richtext> {
    IPage<Richtext> pageS(Page<Richtext> page, Wrapper<Richtext> wrapper);



}
