package com.weee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weee.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (Menu)表服务接口
 *
 * @author makejava
 * @since 2025-04-13 14:10:23
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据 roidId 查询菜单列表
     * @param roidId 权限 ID
     * @return 菜单列表
     */
    List<Menu> getMenuListByRoidId(String roidId);

    /**
     * 分页查询菜单列表
     * @param pageRequest 分页请求参数
     * @return 分页后的菜单列表
     */
    Page<Menu> getMenuListByPage(PageRequest pageRequest);
}