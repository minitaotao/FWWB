package com.example.fwwbjava1_8.Mysql_class.Service;

import com.example.fwwbjava1_8.Mysql_class.Mapper.OrderMapper;
import com.example.fwwbjava1_8.Mysql_class.Mapper.RuleMapper;
import com.example.fwwbjava1_8.Mysql_class.Redis.Cache;
import com.example.fwwbjava1_8.Mysql_class.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public class CheckService {

    @Autowired
    private AuthService authService;
    @Autowired
    private RuleMapper ruleMapper;
    @Autowired
    private ItemService itemService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AdminService adminService;
    @Autowired(required=false)
    Cache cache;
    public boolean CheckAge(int id,Item item){

        RuleAge ruleAge = (RuleAge) cache.get(item.getRule_age(),new RuleAge());
        User user = (User) cache.get(id,new User());

        int age_max = ruleAge.getAge_max();
        int age_min = ruleAge.getAge_min();
        if(age_max==-1)
            return user.getAge()>=age_min;
        else if(age_min==-1)
            return user.getAge()<=age_max;
        else
            return user.getAge()<=age_max && user.getAge()>=age_min;
    }

    public boolean CheckIncome(int id,Item item){

        RuleIncome ruleIncome = (RuleIncome) cache.get(item.getRule_income(),new RuleIncome());
        User user = (User) cache.get(id,new User());

        int income_min = ruleIncome.getIncome_min();
        if(income_min==-1)
            return true;
        else
            return user.getIncome()>=income_min;
    }

    public boolean CheckDefaulter(int id){

        User user = (User) cache.get(id,new User());
        return user.getDefaulter() != 1;
    }

    public boolean CheckPurchaseNum(int id,Item item){
        int count=0;
        List<Order> orders= orderMapper.getOrderListByUserIdAndItemId(id,item.getId());
        for(Order order:orders) {
            count+= order.getItem_quantity();
        }
        return count < item.getPurlimnum();
    }

    public int isPermitted(String token,int itemId){
        int id = authService.SearchIdByToken(token);
        Item item = (Item) cache.get(itemId,new Item());
        if(!CheckAge(id, item))
            return -1;
        else if(!CheckIncome(id, item))
            return -2;
        else if(!CheckDefaulter(id))
            return 0;
        else if(!CheckPurchaseNum(id, item))
            return -3;
        else
            return 1;
        //return CheckAge(token,item) && CheckPurchaseNum(token,item) && CheckDefaulter(token) && CheckIncome(token,itemId);

    }
}
