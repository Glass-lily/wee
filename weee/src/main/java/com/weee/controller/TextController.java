package com.weee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weee.common.QueryPageParam;
import com.weee.common.Result;
import com.weee.entity.Menu;
import com.weee.entity.Text;
import com.weee.entity.Text;
import com.weee.entity.TextRes;
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
        int num  = Optional.ofNullable(textService.list().size()).orElse(1);
        text.setId(num+2);
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


        Page<Text> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Text> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Text::getName, name);
        }


        IPage<TextRes> result = textService.pageS(page, lambdaQueryWrapper);


        return Result.success(result.getRecords(), result.getTotal());
    }

    @GetMapping("/list")
    public Result list() {
        List list= textService.list();
        return Result.success(list);
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam Long id) {
        TextRes textRes = textService.getDetailById(id);
        return textRes != null ? Result.success(textRes) : Result.fail();
    }

}

