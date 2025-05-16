package com.weee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weee.common.QueryPageParam;
import com.weee.common.Result;
import com.weee.entity.*;
import com.weee.entity.Text;
import com.weee.service.TextService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * (Text)表控制层
 *
 * @author makejava
 * @since 2025-04-13 23:02:21
 */
@RestController
@RequestMapping("text")
public class TextController {
    /**
     * 服务对象
     */
    @Resource
    private TextService textService;

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return textService.removeById(id) ? Result.success() : Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Text text) {
        // 不手动设置 id，让数据库自动生成
        return textService.save(text) ? Result.success() : Result.fail();
    }


    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Text text) {
        return textService.updateById(text) ? Result.success() : Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String content = param.get("content") == null ? null : param.get("content").toString();
        String storage = param.get("storage") == null ? null : param.get("storage").toString();
        String goodstype = param.get("goodstype") == null ? null : param.get("goodstype").toString();
        Integer count = (Integer) param.get("count");  // 获取 count 参数

        // 创建分页对象
        Page<Text> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Text> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like("t.name", name);  // 确保字段名和数据库一致
        }
        if (StringUtils.isNotBlank(content)) {
            queryWrapper.like("t.content", content);
        }
        if (StringUtils.isNotBlank(storage)) {
            queryWrapper.eq("s.id", storage);
        }
        if (StringUtils.isNotBlank(goodstype)) {
            queryWrapper.eq("gt.id", goodstype);
        }
        // 加入 count = 1 的条件
        queryWrapper.eq("g.count", 1);  // 只返回 count = 1 的记录

        // 执行查询
        IPage<TextRes> result = textService.pageS(page, queryWrapper);
        return Result.success(result.getRecords(), result.getTotal());
    }

    @GetMapping("/list")
    public Result list() {
        List list = textService.list();
        return Result.success(list);
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Long id) {
        TextRes textRes = textService.getDetailById(id);
        return textRes != null ? Result.success(textRes) : Result.fail();
    }

    /**
     * 根据ID获取知识详情，用于分享功能
     * @param id 知识ID
     * @return 知识详情
     */
    @GetMapping("/share/{id}")
    public Result share(@PathVariable Long id) {
        TextRes textRes = textService.getDetailById(id);
        if (textRes != null) {
            // 返回用于分享的内容，确保包含所有需要的字段（如 name, content, createdAt 等）
            return Result.success(textRes);
        } else {
            return Result.fail();
        }
    }
}

