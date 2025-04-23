package com.weee.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weee.entity.Record;

/**
 * (Record)表服务接口
 *
 * @author makejava
 * @since 2025-04-23 13:58:52
 */
public interface RecordService extends IService<Record> {

    IPage<Record> pageS(Page<Record> page, Wrapper<Record> wrapper);


}
