package com.personal.service.impl;

import com.personal.mapper.TestUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * userService 接口实现类
 *
 * @author sunchao
 * @date 2018/12/7
 */
@Service
@CacheConfig(cacheNames="userCache") // 本类内方法指定使用缓存时，默认的名称就是userCache
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor=Exception.class)
public class UserRedisServiceImpl {
    @Autowired
    private TestUserMapper userMapper;


}
