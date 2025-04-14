package com.weee.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weee.entity.Richtext;
import com.weee.mapper.RichtextMapper;
import com.weee.service.RichtextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (Richtext)表服务实现类
 *
 * @author makejava
 * @since 2025-04-14 15:43:55
 */
@Service("RichtextService")
public class RichtextServiceImpl extends ServiceImpl<RichtextMapper, Richtext> implements RichtextService {
    @Autowired
    private RichtextMapper richtextMapper;

    @Override
    public IPage<Richtext> pageS(Page<Richtext> page, Wrapper<Richtext> wrapper) {
        return baseMapper.selectPage(page, wrapper); // 直接使用 BaseMapper 的 selectPage
    }


}
