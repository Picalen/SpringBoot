package com.personal.redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.personal.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author sc
 * @date 2018-12-06$
 * @description reids service层$
 */
@Repository
public class UserRedis {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public void add(String key, User user, Long time) {
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(user), time, TimeUnit.MINUTES);
    }

    public void add(String key, List<User> users, Long time) {
        Gson gson = new Gson();
        String src = gson.toJson(users);
        redisTemplate.opsForValue().set(key, src, time, TimeUnit.MINUTES);
    }

    public User get(String key) {
        String source = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(source)) {
            return new Gson().fromJson(source, User.class);
        }
        return null;
    }

    public List<User> getUserList(String key) {
        String source = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(source)) {
            return new Gson().fromJson(source, new TypeToken<List<User>>() {
            }.getType());
        }
        return null;
    }

    public void delete(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }

}
