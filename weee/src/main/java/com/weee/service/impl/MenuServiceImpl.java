package com.weee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weee.entity.Menu;
import com.weee.mapper.MenuMapper;
import com.weee.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Menu)表服务实现类
 *
 * @author makejava
 * @since 2025-04-13 14:10:23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getMenuListByRoidId(String roidId) {
        return this.lambdaQuery().eq(Menu::getMenuright, roidId).list();
    }

    @Override
    public Page<Menu> getMenuListByPage(PageRequest pageRequest) {
        int offset = (int) pageRequest.getOffset();
        int limit = pageRequest.getPageSize();
        List<Menu> menuList = menuMapper.selectPage(offset, limit);
        long total = this.count();
        return new PageImpl<>(menuList, pageRequest, total);
    }
}