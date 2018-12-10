package com.personal.service.impl;

import com.personal.mapper.TestUserMapper;
import com.personal.model.User;
import com.personal.service.inter.UserRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * userService 接口实现类
 *
 * @Cacheable：用于读取方法，如果读的是数据库，就把返回值放入缓存
 * value：缓存名
 * key:缓存key
 * condition：缓存条件，为true才执行缓存操作
 * @CachePut ：用于新增/修改方法，效果同 @Cacheable
 * 参数同上
 * @CacheEvict：用于删除方法，删除对应数据同时，删除缓存
 * 除以上参数外，还包括
 * allEntries：是否清空所有缓存
 * beforeInvocation：是否在方法执行前就清空
 *
 * @author sunchao
 * @date 2018/12/7
 */
@Service("userRedisImpl")
public class UserRedisServiceImpl implements UserRedisService {
    @Autowired
    private TestUserMapper userMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Cacheable(value = "user", key = "#user.getId()", unless = "#result eq null")
    @Override
    public User findById(User user) {
        System.out.println("=====================缓存中查找失败，从数据库中获取，id="+user.getId());
        return userMapper.findById(user);
    }

    @CacheEvict(value="user1",key = "user.getId()")
    @Override
    public User updateUser(User user) {
        System.out.println("===================从数据库中更新，从缓存中删除,id="+user.getId());
        if(userMapper.updateUser(user) > 0){
            return user;
        }
        return null;
    }

    @CachePut(value="user1",key = "user.getId()")
    @Override
    public User addUser(User user) {
        System.out.println("===================新增用户到数据库,id="+user.getId());
        User oldUser = userMapper.findById(user);
        if(null != oldUser) return null;
        if(userMapper.insertUser(user) > 0){
            return user;
        }
        return null;
    }

    @CacheEvict(value="user1",key = "user.getId()")
    @Override
    public boolean deleteFromCache(User user) {
        System.out.println("===================从数据库中删除，从缓存中删除,id="+user.getId());
        return userMapper.deleteUserById(user)>0;
    }


}
