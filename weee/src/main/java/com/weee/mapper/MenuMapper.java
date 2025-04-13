package com.weee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weee.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (Menu)表数据库访问层
 *
 * @author makejava
 * @since 2025-04-13 14:10:23
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 分页查询菜单列表
     * @param offset 偏移量
     * @param limit 每页数量
     * @return 菜单列表
     */
    List<Menu> selectPage(int offset, int limit);
}