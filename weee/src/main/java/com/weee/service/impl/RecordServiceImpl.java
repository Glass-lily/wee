package com.weee.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weee.entity.Record;
import com.weee.mapper.RecordMapper;
import com.weee.service.RecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
/**
 * (Record)表服务实现类
 *
 * @author makejava
 * @since 2025-04-23 13:58:52
 */
@Service("recordService")
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
    @Resource
    private RecordMapper recordMapper;

    @Override
    public IPage pageS(Page<Record> page, Wrapper<Record> wrapper) {
        return recordMapper.pageS(page, wrapper);
    }
}
