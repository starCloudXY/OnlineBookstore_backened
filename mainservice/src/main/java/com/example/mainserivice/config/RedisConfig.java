package com.example.mainserivice.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        ObjectMapper om = new ObjectMapper();
        //制定序列化的域,field,get和set，以及修饰符范围
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(om);
        //使用json 序列化
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();

        return template;
    }

    /*
    * 对hash类型的数据操作
    * */
    @Bean
    public ValueOperations<String,Object> hashOperations(RedisTemplate<String,Object> redisTemplate){
        return redisTemplate.opsForValue();
    }
    /*
    * 对redis类型的数据操作
    * */
    @Bean
    public ValueOperations<String,Object> valueOperations(RedisTemplate<String,Object> redisTemplate){
        return redisTemplate.opsForValue();
    }
    /*
    * 对链表类型的数据操作
    * */
    @Bean
    public ListOperations<String,Object> listOperations(RedisTemplate<String,Object> redisTemplate){
        return redisTemplate.opsForList();
    }
    /*
    * 对于无序集合的数据操作
    * */
    @Bean
    public SetOperations<String,Object> setOperations(RedisTemplate<String,Object> redisTemplate){
        return redisTemplate.opsForSet();
    }
    /*
    * 对有序集合的数据操作
    * */
    @Bean
    public ZSetOperations<String,Object> zSetOperations(RedisTemplate<String,Object> redisTemplate){
        return redisTemplate.opsForZSet();
    }
}
