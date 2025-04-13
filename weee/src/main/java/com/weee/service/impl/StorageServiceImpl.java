package com.weee.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weee.entity.Storage;
import com.weee.mapper.StorageMapper;
import com.weee.service.StorageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("storageService")
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {
    @Resource
    private StorageMapper storageMapper;

    @Override
    public IPage<Storage> pageS(Page<Storage> page, Wrapper<Storage> wrapper) {
        return baseMapper.selectPage(page, wrapper); // 直接使用 BaseMapper 的 selectPage
    }
}