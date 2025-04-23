package com.weee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weee.common.QueryPageParam;
import com.weee.common.Result;
import com.weee.entity.Record;
import com.weee.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * (Record)表控制层
 *
 * @author makejava
 * @since 2025-04-23 13:58:50
 */
@Slf4j
@RestController
@RequestMapping("record")
public class RecordController {
    @Autowired
    private RecordService recordService;


    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = param.get("name").toString();
        String storage =  param.get("storage").toString();
        String goodstype =  param.get("goodstype").toString();


        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Record> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply(" a.goods=b.id AND b.storage=c.id AND b.goodsType=d.id ");
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            queryWrapper.like("b.name",name);
        }
        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
            queryWrapper.eq("c.id",storage);
        }
        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)) {
            queryWrapper.eq("d.id",goodstype);
        }


        IPage result = recordService.pageS(page, queryWrapper);


        return Result.success(result.getRecords(), result.getTotal());
    }
//
//    @GetMapping("/list")
//    public Result list() {
//        List list= recordService.list();
//        System.out.println(list);
//        return Result.success(list);
//    }

}

