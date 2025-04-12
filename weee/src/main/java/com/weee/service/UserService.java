package com.weee.service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weee.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    public List<User> selectAll();

    IPage pageC(IPage<User> page, LambdaQueryWrapper<User> lambdaQueryWrapper);

    IPage pageCC(Page<User> page, Wrapper wrapper);
}
