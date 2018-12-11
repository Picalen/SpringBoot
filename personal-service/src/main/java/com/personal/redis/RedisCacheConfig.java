//package com.personal.redis;
//
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * @author sc
// * @date 2018-12-10$
// * @description redis 基础配置類
// */
//@Configuration//SpringBoot注解，启动时会注入
//@EnableCaching //启用缓存
//public class RedisCacheConfig extends CachingConfigurerSupport {
//
//    /**
//     * 缓存管理器
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate) {
//        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
//        return rcm;
//    }
//
//    /**
//     * redis模板操作类
//     * @param factory 由springboot注入
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
//        redisTemplate.setConnectionFactory(factory);
//        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//        //key序列化方式，在定义了key生成策略之后再加以下代码
//    	RedisSerializer<String> redisSerializer = new StringRedisSerializer();
//        //key序列化方式
//        redisTemplate.setKeySerializer(redisSerializer);
//        //value序列化
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        //value hashmap序列化
//        redisTemplate.setHashValueSerializer(redisSerializer);
//
//        redisTemplate.afterPropertiesSet();
//
//        return redisTemplate;
//    }
//
//}
