package com.example.mainserivice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public RedisUtil(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }
    @SuppressWarnings("unchecked")

    public void del(String ... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                redisTemplate.delete(key[0]);
            }
            else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }
    /**
     * 获取（通过Key）
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        try {
            return key==null?null:redisTemplate.opsForValue().get(key);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 放入Redis缓存之中
     * @param key 键
     * @param value 值
     * */
    public Boolean set(String key,Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            System.out.println("Redis Set"+key+" "+value);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
