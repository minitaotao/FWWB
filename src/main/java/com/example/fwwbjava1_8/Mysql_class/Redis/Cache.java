package com.example.fwwbjava1_8.Mysql_class.Redis;

import com.example.fwwbjava1_8.Mysql_class.Mapper.ItemMapper;
import com.example.fwwbjava1_8.Mysql_class.Mapper.RuleMapper;
import com.example.fwwbjava1_8.Mysql_class.Mapper.UserMapper;
import com.example.fwwbjava1_8.Mysql_class.model.Item;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class Cache {
    final ItemMapper itemMapper;
    final RedisTemplate redisTemplate;
    final UserMapper userMapper;
    final RuleMapper ruleMapper;

    public Cache(ItemMapper itemMapper, RedisTemplate redisTemplate, UserMapper userMapper, RuleMapper ruleMapper){
        this.itemMapper = itemMapper;
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
        this.ruleMapper = ruleMapper;
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        List<Item> itemList = itemMapper.getItemList();
        for(Item item : itemList){
            redisTemplate.opsForHash().put("Item",item.getId().toString(),item);
        }
    }

    public List<Item> getItemList() throws InvocationTargetException, IllegalAccessException {
        List<Item> itemList = new LinkedList<>();
        Map<String, Item> itemsMap = (Map<String, Item>) redisTemplate.opsForHash().entries("Item");
        for (Item item : itemsMap.values()){
            itemList.add(item);
        }

        return itemList;
    }


    public Object get(int id,Object obj) {
        try{

        Class it = obj.getClass();

        if(redisTemplate.opsForHash().hasKey(it.getSimpleName(),String.valueOf(id))){
            obj = redisTemplate.opsForHash().get(it.getSimpleName(),String.valueOf(id));
        }
        else{
            String mapperName,methodName;
            if(it.getSimpleName().startsWith("Rule")){
                mapperName = "ruleMapper";
                methodName = "get"+it.getSimpleName().substring(4);
            }
            else {
                mapperName = it.getSimpleName().toLowerCase().concat("Mapper");
                methodName = "get"+it.getSimpleName();
            }
            Field mapper = this.getClass().getDeclaredField(mapperName);
            obj = mapper.get(this).getClass().getMethod(methodName,Integer.class).invoke(mapper.get(this),id);
            redisTemplate.opsForHash().put(it.getSimpleName(),String.valueOf(id),obj);
        }
        }catch (Exception e) {
            return null;
        }
        return obj;
    }

}