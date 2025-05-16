package com.weee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weee.common.QueryPageParam;
import com.weee.common.Result;
import com.weee.entity.Goods;
import com.weee.entity.Text;
import com.weee.service.GoodsService;
import com.weee.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

/**
 * (Goods)表控制层
 *
 * @author makejava
 * @since 2025-04-14 12:20:47
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    /**
     * 服务对象
     */
    @Autowired
    private GoodsService goodsService;

    @Resource
    private TextService textService;

    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return goodsService.removeById(id) ? Result.success() : Result.fail();
    }

    @PostMapping("/save")
    @Transactional
    public Result save(@RequestBody Goods goods) {
        try {
            goods.setId(null); // 确保 ID 为空，防止覆盖现有记录
            if (goods.getCount() == null) {
                goods.setCount(0); // 设置默认值
            }
            boolean goodsSaved = goodsService.save(goods);
            if (!goodsSaved) {
                return Result.fail();
            }
            return Result.success();
        } catch (DuplicateKeyException e) {
            return Result.fail();
        } catch (Exception e) {
            return Result.fail();
        }
    }

    // 更新
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods) {
        try {
            boolean success = goodsService.updateById(goods);
            return success ? Result.success() : Result.fail();
        } catch (Exception e) {
            return Result.fail();
        }
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = param.get("name").toString();
        String storage = param.get("storage").toString();
        String goodstype = param.get("goodstype").toString();
     //   String remark =param.get("remark").toString();

        Page<Goods> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Goods::getName, name);
        }
        if (StringUtils.isNotBlank(storage) && !"null".equals(storage)) {
            lambdaQueryWrapper.eq(Goods::getStorage, storage);
        }
        if (StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)) {
            lambdaQueryWrapper.eq(Goods::getGoodstype, goodstype);
        }

        // 执行分页查询
        IPage<Goods> result = goodsService.page(page, lambdaQueryWrapper);
        return Result.success(result.getRecords(), result.getTotal());
    }

    @PostMapping("/updateAuditStatus")
    public Result updateAuditStatus(@RequestParam Integer id, @RequestParam Integer count) {
        try {
            boolean isUpdated = goodsService.updateAuditStatus(id, count);  // 更新商品的审核状态
            if (isUpdated) {
                return Result.success("状态更新成功");
            } else {
                return Result.fail();
            }
        } catch (Exception e) {
            return Result.fail();
        }
    }
        //上传图片
        @PostMapping("/uploadImage")
        public Result uploadImage(
                @RequestParam("file") MultipartFile file,
                @RequestParam("id") Integer id) {
            try {
                // 1. 构造文件名并保存到磁盘
                String original = file.getOriginalFilename();
                String ext = original.substring(original.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + ext;
                Path uploadPath = Paths.get("upload/images", fileName);
                Files.createDirectories(uploadPath.getParent());
                Files.copy(file.getInputStream(), uploadPath);

                // 2. 构造对外访问的 URL
                String imageUrl = "/images/" + fileName;

                // 3. 只更新该商品的 remark 字段
                Goods g = new Goods();
                g.setId(id);
                g.setRemark(imageUrl);
                goodsService.updateById(g);

                // 4. 返回给前端预览用的 URL
                return Result.success(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                return Result.fail();
            }
        }


}