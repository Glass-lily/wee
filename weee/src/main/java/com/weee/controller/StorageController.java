package com.weee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weee.common.QueryPageParam;
import com.weee.common.Result;
import com.weee.entity.Menu;
import com.weee.entity.Storage;
import com.weee.entity.Storage;
import com.weee.service.StorageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * (Storage)表控制层
 *
 * @author makejava
 * @since 2025-04-13 23:02:21
 */
@RestController
@RequestMapping("storage")
public class StorageController {
    /**
     * 服务对象
     */
    @Resource
    private StorageService storageService;

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return storageService.removeById(id) ? Result.success() : Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage) {
        int num  = Optional.ofNullable(storageService.list().size()).orElse(1);
        storage.setId(num+2);
        return storageService.save(storage) ? Result.success() : Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Storage storage) {
        return storageService.updateById(storage) ? Result.success() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");


        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Storage> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Storage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Storage::getName, name);
        }


        IPage result = storageService.pageS(page, lambdaQueryWrapper);


        return Result.success(result.getRecords(), result.getTotal());
    }

    @GetMapping("/list")
    public Result list() {
        List list= storageService.list();
        return Result.success(list);
    }

}

