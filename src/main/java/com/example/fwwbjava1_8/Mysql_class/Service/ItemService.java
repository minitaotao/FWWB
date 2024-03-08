package com.example.fwwbjava1_8.Mysql_class.Service;

import com.example.fwwbjava1_8.Mysql_class.Mapper.ItemMapper;
import com.example.fwwbjava1_8.Mysql_class.Redis.Cache;
import com.example.fwwbjava1_8.Mysql_class.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public class ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private Cache cache;
    @Autowired
    private RedisTemplate redisTemplate;


    public List<Item> getItemList() {
        return itemMapper.getItemList();
    }

    public String AddItem(Item item) {
        item.setStock(item.getTotalstock());
        int id = itemMapper.AddItem(item);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        List<Item> itemList = itemMapper.getItemList();
        for(Item item0 : itemList){
            redisTemplate.opsForHash().put("Item",item0.getId().toString(),item0);
        }
        return "添加成功数据";
    }

    public Item getItem(int id) {
        return (Item) cache.get(id,new Item());
    }

    public String DeleteItem(int id) {
        int i = itemMapper.DeleteItem(id);
        return "成功删除" + i + "条数据";
    }

    public String ChangeItem(Item item) {
        int i;
        try{
        PurchaseService.lock.lock();

        Item tempItem = itemMapper.getItem(item.getId());
        if(tempItem.getStock() > item.getTotalstock()){
            return "修改失败";
        }
        item.setStock(item.getTotalstock()-(tempItem.getTotalstock()- tempItem.getStock()));
        i = itemMapper.ChangeItem(item);
        redisTemplate.opsForHash().put("Item",item.getId().toString(),item);
        return "成功修改" + i + "条数据";
        } finally {
            PurchaseService.lock.unlock();
        }
    }

}