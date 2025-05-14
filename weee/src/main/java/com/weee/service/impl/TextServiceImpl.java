package com.weee.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weee.entity.Text;
import com.weee.entity.TextRes;
import com.weee.mapper.TextMapper;
import com.weee.service.TextService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * (Record)表服务实现类
 *
 * @author makejava
 * @since 2025-04-23 13:58:52
 */
@Service
public class TextServiceImpl extends ServiceImpl<TextMapper, Text> implements TextService {
    @Resource
    private TextMapper textMapper;

    @Override
    public IPage<TextRes> pageS(Page<Text> page, Wrapper<Text> wrapper) {
        return textMapper.pageS(page, wrapper);
    }

    //  新增：根据ID查询详情
    @Override
    public TextRes getDetailById(Long id) {
        return textMapper.getDetailById(id);
    }
}
