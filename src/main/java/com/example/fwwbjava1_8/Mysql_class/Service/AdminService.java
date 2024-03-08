package com.example.fwwbjava1_8.Mysql_class.Service;

import com.example.fwwbjava1_8.Mysql_class.Mapper.AdminMapper;

import com.example.fwwbjava1_8.Mysql_class.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Repository
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    static Map<String, Integer> map = new ConcurrentHashMap();

    public Admin AdminLogin(String account, String password) {
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        return adminMapper.AdminLogin(account, password);
    }

    public void AddToken(String uuid, int userID) {
        map.forEach((tk,id) ->{
            if (id==userID){
                map.remove(tk,id);
            }
        });
        map.put(uuid, userID);
    }

    public Admin SearchToken(String token) {
        if (!map.containsKey(token)){
            return null;
        }
        int UserId = map.get(token);
        if (UserId == 0) {
            return null;
        } else {
            return SearchUserById(UserId);
        }
    }

    public Admin SearchUserById(int id) {
        return adminMapper.GetAdminById(id);
    }

    public boolean isAdmin(String token){
        return SearchToken(token)!=null;
    }
}
