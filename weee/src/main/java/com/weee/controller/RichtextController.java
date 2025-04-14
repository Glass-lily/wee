package com.weee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weee.common.QueryPageParam;
import com.weee.common.Result;
import com.weee.entity.Richtext;
import com.weee.service.RichtextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Optional;

/**
 * (Richtext)表控制层
 *
 * @author hk
 * @since 2025-04-14 15:43:55
 */
@RestController
@RequestMapping("richtext")
public class RichtextController {
    @Autowired
    private RichtextService richtextService;
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return richtextService.removeById(id) ? Result.success() : Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Richtext richtext) {
        int num  = Optional.ofNullable(richtextService.list().size()).orElse(1);
        richtext.setId(num+2);
        return richtextService.save(richtext) ? Result.success() : Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Richtext richtext) {
        return richtextService.updateById(richtext) ? Result.success() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String title = (String) param.get("title");


        Page<Richtext> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Richtext> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(title) && !"null".equals(title)) {
            lambdaQueryWrapper.like(Richtext::getTitle, title);
        }

        IPage result = richtextService.pageS(page, lambdaQueryWrapper);

        return Result.success(result.getRecords(), result.getTotal());
    }

}

