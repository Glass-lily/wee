package com.weee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weee.common.QueryPageParam;
import com.weee.common.Result;
import com.weee.entity.Goodstype;
import com.weee.service.GoodstypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Optional;

/**
 * (Goodstype)表控制层
 *
 * @author makejava
 * @since 2025-04-14 11:57:21
 */
@RestController
@RequestMapping("goodstype")
public class GoodstypeController {
    /**
     * 服务对象
     */
    @Resource
    private GoodstypeService goodstypeService;

    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return goodstypeService.removeById(id) ? Result.success() : Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Goodstype goodstype) {
        int num  = Optional.ofNullable(goodstypeService.list().size()).orElse(1);
        goodstype.setId(num+2);
        return goodstypeService.save(goodstype) ? Result.success() : Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Goodstype goodstype) {
        return goodstypeService.updateById(goodstype) ? Result.success() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");


        Page<Goodstype> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goodstype> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Goodstype::getName, name);
        }


        IPage result = goodstypeService.pageS(page, lambdaQueryWrapper);


        return Result.success(result.getRecords(), result.getTotal());
    }

}

