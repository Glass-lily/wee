package com.weee.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weee.entity.Storage;


/**
 * (Storage)表服务接口
 *
 * @author makejava
 * @since 2025-04-13 23:02:33
 */
public interface StorageService extends IService<Storage> {
    IPage<Storage> pageS(Page<Storage> page, Wrapper<Storage> wrapper);

}
