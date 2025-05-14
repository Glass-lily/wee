package com.weee.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.weee.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;

public interface GoodsMapper extends BaseMapper<Goods> {
    IPage pageS(Page<Goods> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    // 根据 ID 查询商品
    @Select("SELECT * FROM goods WHERE id = #{id}")
    Goods selectById(Integer id);

    // 更新商品的审核状态
    @Update("UPDATE goods SET count = #{count} WHERE id = #{id}")
    int updateById(Goods goods);
}
