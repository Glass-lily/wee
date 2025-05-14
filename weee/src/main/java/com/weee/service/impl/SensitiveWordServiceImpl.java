package com.weee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weee.entity.SensitiveWord;
import com.weee.mapper.SensitiveWordMapper;
import com.weee.service.SensitiveWordService;
import org.springframework.stereotype.Service;

@Service
public class SensitiveWordServiceImpl extends ServiceImpl<SensitiveWordMapper, SensitiveWord> implements SensitiveWordService {

}
