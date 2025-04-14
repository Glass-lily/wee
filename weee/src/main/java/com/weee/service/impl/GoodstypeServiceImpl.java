package com.weee.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weee.entity.Goodstype;
import com.weee.mapper.GoodstypeMapper;
import com.weee.service.GoodstypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * (Goodstype)表服务实现类
 *
 * @author makejava
 * @since 2025-04-14 11:57:22
 */
@Service("goodstypeService")
public class GoodstypeServiceImpl extends ServiceImpl<GoodstypeMapper, Goodstype> implements GoodstypeService {
    @Resource
    private GoodstypeMapper goodstypeMapper;

    @Override
    public IPage<Goodstype> pageS(Page<Goodstype> page, Wrapper<Goodstype> wrapper) {
        return baseMapper.selectPage(page, wrapper); // 直接使用 BaseMapper 的 selectPage
    }
}
