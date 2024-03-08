package com.example.fwwbjava1_8.Mysql_class.Service;

import com.example.fwwbjava1_8.Mysql_class.Mapper.UserMapper;
import com.example.fwwbjava1_8.Mysql_class.Mapper.ItemMapper;
import com.example.fwwbjava1_8.Mysql_class.Mapper.BillsMapper;
import com.example.fwwbjava1_8.Mysql_class.Mapper.OrderMapper;
import com.example.fwwbjava1_8.Mysql_class.Redis.Cache;
import com.example.fwwbjava1_8.Mysql_class.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Repository
public class PurchaseService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    BillsMapper billsMapper;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    UserMapper userMapper;
    RedisTemplate redisTemplate;
    @Autowired
    Cache cache;
    public static final Lock lock = new ReentrantLock();

    public PurchaseService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisTemplate.setKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        this.redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
    }

    public ResultData AddOrderANDBill(int item_id, int user_id, int item_quantity) {
        Order order = new Order();
        order.setBuyer_id(user_id);
        order.setItem_id(item_id);
        order.setItem_quantity(item_quantity);
        Item item;
        User user;
        //查看商品
        try {
            lock.lock();
            item = (Item) cache.get(item_id, new Item());
            user = (User) cache.get(user_id, new User());
            if (item.getBeginDate() > (System.currentTimeMillis() / 1000) || item.getEndDate() < (System.currentTimeMillis() / 1000)) {
                return ResultData.fail(500, "未在购买时间");
            }
            if ((item.getPrice()) * (order.getItem_quantity()) > (user.getBalance())) {
                return ResultData.fail(500, "余额不足");
            }
            if (item.getStock() < order.getItem_quantity()) {
                return ResultData.fail(500, "商品不足");
            }
            //设置用户数据
            user.setBalance(user.getBalance() - order.getItem_quantity() * item.getPrice());
            redisTemplate.opsForHash().put("User", user.getId().toString(), user);

            //设置商品数据
            item.setStock(item.getStock() - order.getItem_quantity());
            redisTemplate.opsForHash().put("Item", item.getId().toString(), item);
        }finally {
            lock.unlock();
        }

        //设置bills与order
        Bill bill = new Bill();
        bill.setUser_id(order.getBuyer_id());
        bill.setItem_id(order.getItem_id());
        bill.setDate(System.currentTimeMillis()/1000);
        bill.setSpending(item.getPrice());
        bill.setSurplus(user.getBalance() - item.getPrice()*order.getItem_quantity());
        UUID billsid = UUID.randomUUID();//order_number
        order.setOrder_number(billsid.toString());

        redisTemplate.opsForHash().put("Bill",billsid.toString(),bill);

        return ResultData.success("购买成功");
    }


    @Scheduled(cron = "0/10 * * * * ?")
    @Async
    public void timingTest(){
        try {
            //更新item_stock
            lock.lock();
            if (redisTemplate.hasKey("Item")) {
                Map<String, Item> itemsMap = (Map<String, Item>) redisTemplate.opsForHash().entries("Item");
                lock.unlock();
                itemsMap.forEach((k, v) -> itemMapper.UpdateItemStock(v));
            } else {
                lock.unlock();
            }

            //更新User_balance
            lock.lock();
            if (redisTemplate.hasKey("User")) {
                Map<String, User> usersMap = (Map<String, User>) redisTemplate.opsForHash().entries("User");
                lock.unlock();
                usersMap.forEach((k, v) -> userMapper.UpdateUserBalance(v));
            } else {
                lock.unlock();
            }

            lock.lock();
            if (redisTemplate.hasKey("Bill")) {
                Map<String, Bill> Bill_Hashkeys = redisTemplate.opsForHash().entries("Bill");
                redisTemplate.delete("Bill");
                lock.unlock();
                for (String hashkey : Bill_Hashkeys.keySet()) {
                    Order order = new Order();
                    Bill bill = Bill_Hashkeys.get(hashkey);
                    billsMapper.AddBill(bill);
                    order.setItem_quantity(1);
                    order.setOrder_number(hashkey);
                    order.setDate(bill.getDate());
                    order.setItem_id(bill.getItem_id());
                    order.setBuyer_id(bill.getUser_id());
                    orderMapper.AddOrder(order);
                    userMapper.UpdateBankBalance(bill.getSpending());
                }
            }else{
                lock.unlock();
            }
        }catch (Exception e){
            lock.unlock();
            throw e;
        }

    }
}
