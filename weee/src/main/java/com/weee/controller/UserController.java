package com.weee.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.weee.common.QueryPageParam;
import com.weee.common.Result;
import com.weee.entity.Menu;
import com.weee.entity.User;
import com.weee.service.MenuService;
import com.weee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        List list= userService.lambdaQuery().eq(User::getNo, no).list();
        return !list.isEmpty() ? Result.success(list):Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return userService.removeById(id)?Result.success():Result.fail();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        return userService.save(user)?Result.success():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        return userService.updateById(user)?Result.success():Result.fail();
    }
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        List list= userService.lambdaQuery()
                .eq(User::getNo, user.getNo())
                .eq(User::getPassword,user.getPassword())
                .list();

        if (!list.isEmpty()){
            User user1=(User)list.getFirst();
            List menulist= menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            HashMap res = new HashMap();
            res.put("user",user1);
            res.put("menu",menulist);
            return Result.success(res);
        }
        return Result.fail();
    }


    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return userService.removeById(id);
    }

    //查询（模糊，匹配）
    @PostMapping("/search")
    public Result search(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(user.getName())) {
            lambdaQueryWrapper.like(User::getName, user.getName());
        }

        return Result.success(userService.list(lambdaQueryWrapper));
    }


    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam queryPageParam) {
//        System.out.println(queryPageParam);
//
//        System.out.println("num= "+queryPageParam.getPageNum());
//        System.out.println("size= "+queryPageParam.getPageSize());

        HashMap param = queryPageParam.getParam();
        String name = param.get("name").toString();
//        System.out.println("param.name= "+param.get("name"));
//        System.out.println("param.no= "+param.get("no"));

        //分页
        Page<User> page=new Page(queryPageParam.getPageNum(),queryPageParam.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName, name);

        IPage result = userService.page(page, lambdaQueryWrapper);

        System.out.println("total=  "+result.getTotal());

        return result.getRecords();
    }

//自定义的

    @PostMapping("/listPageC")
    public Result listPageC(@RequestBody QueryPageParam queryPageParam) {

        HashMap param = queryPageParam.getParam();
//        String name = param.get("name").toString();
        Object nameObj = param.get("name");
        String name = (nameObj != null) ? nameObj.toString() : null;

        // 如果name为null，可以选择不进行查询或设置一个默认值
        if (name == null) {
            name = ""; // 如果name为null，可以将其设置为一个空字符串或其他默认值
        }
        Object sexObj = param.get("sex");
        String sex = (sexObj != null) ? sexObj.toString() : null;

        // 如果sex为null，可以选择不进行查询或设置一个默认值
        if (sex == null) {
            sex = ""; // 如果sex为null，可以将其设置为一个空字符串或其他默认值
        }

        //分页
        Page<User> page=new Page<>(queryPageParam.getPageNum(),queryPageParam.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) &&!"null".equals(name)) {
            lambdaQueryWrapper.like(User::getName, name);
        }
        if(StringUtils.isNotBlank(sex)){
            lambdaQueryWrapper.eq(User::getSex,sex);
        }


       IPage result = userService.pageCC(page,lambdaQueryWrapper);

//        System.out.println("total=  "+result.getTotal());

        return Result.success(result.getRecords(),result.getTotal());
    }


    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String sex = (String)param.get("sex");
        String roleId = (String)param.get("roleId");

        Page<User> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getName,name);
        }
        if(StringUtils.isNotBlank(sex)){
            lambdaQueryWrapper.eq(User::getSex,sex);
        }
        if(StringUtils.isNotBlank(roleId)){
            lambdaQueryWrapper.eq(User::getRoleId,roleId);
        }

        //IPage result = userService.pageC(page);
        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return Result.success(result.getRecords(),result.getTotal());
    }



}
