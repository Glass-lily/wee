package com.weee.controller;

import com.weee.entity.SensitiveWord;
import com.weee.service.SensitiveWordService;
import com.weee.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensitive")
public class SensitiveWordController {

    @Resource
    private SensitiveWordService sensitiveWordService;

    // 获取所有敏感词内容（只返回 word 列）
    @GetMapping("/list")
    public Result list() {
        List<SensitiveWord> list = sensitiveWordService.list();
        return Result.success(list);
    }

    // 根据 ID 获取敏感词
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        SensitiveWord sensitiveWord = sensitiveWordService.getById(id);
        if (sensitiveWord != null) {
            return Result.success(sensitiveWord);
        } else {
            return Result.error("敏感词未找到");
        }
    }

    // 添加敏感词
    @PostMapping("/add")
    public Result add(@RequestBody SensitiveWord sensitiveWord) {
        boolean saved = sensitiveWordService.save(sensitiveWord);
        if (saved) {
            return Result.success("敏感词添加成功");
        } else {
            return Result.error("敏感词添加失败");
        }
    }

    // 删除敏感词
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id) {
        boolean removed = sensitiveWordService.removeById(id);
        if (removed) {
            return Result.success("敏感词删除成功");
        } else {
            return Result.error("敏感词删除失败");
        }
    }

}
