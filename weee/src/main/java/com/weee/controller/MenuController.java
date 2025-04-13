package com.weee.controller;

import com.weee.common.Result;
import com.weee.entity.Menu;
import com.weee.entity.User;
import com.weee.service.MenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Menu)表控制层
 *
 * @author makejava
 * @since 2025-04-13 14:10:22
 */
@RestController
@RequestMapping("menu")
public class MenuController {
    /**
     * 服务对象
     */
    @Resource
    private MenuService menuService;

    @GetMapping("/list")
    public Result list(@RequestParam String roidId) {
        List list= menuService.lambdaQuery().like(Menu::getMenuright, roidId).list();
        return Result.success(list);
    }



}

