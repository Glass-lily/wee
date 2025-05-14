package com.weee.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weee.entity.Goods;
import com.weee.mapper.GoodsMapper;
import com.weee.service.GoodsService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * (Goods)表服务实现类
 *
 * @author makejava
 * @since 2025-04-14 12:20:47
 */
@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public IPage<Goods> pageS(Page<Goods> page, Wrapper<Goods> wrapper) {
        return baseMapper.selectPage(page, wrapper); // 直接使用 BaseMapper 的 selectPage
    }

    @Override
    public boolean updateAuditStatus(Integer id, Integer count) {
        Goods goods = goodsMapper.selectById(id);  // 根据 ID 查询商品
        if (goods != null) {
            goods.setCount(count);  // 设置新的审核状态
            int rows = goodsMapper.updateById(goods);  // 执行更新操作
            return rows > 0;  // 如果更新了记录，返回 true
        }
        return false;  // 商品不存在时，返回 false
    }
}
