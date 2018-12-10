package com.personal.service.inter;

import com.personal.mapper.TestUserMapper;
import com.personal.model.User;
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
public interface UserRedisService {

    User findById(User user);

    boolean deleteFromCache(User user);

    User updateUser(User user);

    User addUser(User user);

}
