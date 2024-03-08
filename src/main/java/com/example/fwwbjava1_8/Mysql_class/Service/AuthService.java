package com.example.fwwbjava1_8.Mysql_class.Service;

import com.example.fwwbjava1_8.Mysql_class.Mapper.OrderMapper;
import com.example.fwwbjava1_8.Mysql_class.Mapper.UserMapper;
import com.example.fwwbjava1_8.Mysql_class.Redis.Cache;
import com.example.fwwbjava1_8.Mysql_class.model.CompleteUserInfo;
import com.example.fwwbjava1_8.Mysql_class.model.RegisterForm;
import com.example.fwwbjava1_8.Mysql_class.model.User;
import com.example.fwwbjava1_8.Mysql_class.util.Sm3Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Repository
public class AuthService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    Cache cache;
    static Map<String, Integer> map1 = new ConcurrentHashMap();
    static Map<Integer, Long> map2 = new ConcurrentHashMap();

    public boolean isRegistered(RegisterForm form) {
        return userMapper.IsRegistered(form) != null;
    }

    public void register(RegisterForm form) {
        form.password = Sm3Utils.encrypt(form.password);
        userMapper.Register(form);
    }

    public void completeUserInfo(CompleteUserInfo completeUserInfo) {
        userMapper.CompleteInfo(completeUserInfo);
        User user;
        try {
            user = (User)cache.get(completeUserInfo.getId(),new User());
        } catch (Exception ignored) {
            return;
        }
        assert user != null;
        if (completeUserInfo.getAge() != null) {
            user.setAge(completeUserInfo.getAge());
        }
        if (completeUserInfo.getIncome() != null) {
            user.setIncome(completeUserInfo.getIncome());
        }
        if (completeUserInfo.getWorking_status() != null) {
            user.setWorking_status(completeUserInfo.getWorking_status());
        }
        redisTemplate.opsForHash().put("User", completeUserInfo.getId().toString(), user);

    }

    public boolean isComplete(User user) {
        return user.getAge() != 0 && user.getIncome() != 0 && (user.getWorking_status() == 0 || user.getWorking_status() == 1);
    }

    public User login(String account, String password) {
        password = Sm3Utils.encrypt(password);
        //password = DigestUtils.md5DigestAsHex(password.getBytes());
        return userMapper.login(account, password);
    }

    static public void AddToken(String uuid, int userID) {
        //id与token的map
        map1.forEach((tk, id) -> {
            if (id == userID) {
                map1.remove(tk, id);
            }
        });
        map1.put(uuid, userID);

        //id与time的map
        map2.forEach((id, time) -> {
            if (id == userID) {
                map2.remove(id, time);
            }
        });
        map2.put(userID, System.currentTimeMillis() / 1000);//以秒为单位
    }

    public boolean IsTokenExpired(int userId) {
        long time = System.currentTimeMillis() / 1000;
        long timeDecline = time - map2.get(userId);
        return timeDecline > 3600*12;
    }

    public int SearchIdByToken(String token) {
        return map1.get(token);
    }

    public User SearchUserByToken(String token) {
        int UserId;
        try{
             UserId = SearchIdByToken(token);
        }catch (Exception Ignored){
            return null;
        }
        if (UserId == 0) {
            return null;
        } else {
            return SearchUserById(UserId);
        }
    }

    public User SearchUserById(int id) {
        return (User)cache.get(id,new User());
    }

}


