package com.example.fwwbjava1_8.Mysql_class.Controller;

import com.example.fwwbjava1_8.Mysql_class.Redis.Cache;
import com.example.fwwbjava1_8.Mysql_class.Service.AdminService;
import com.example.fwwbjava1_8.Mysql_class.Service.AuthService;
import com.example.fwwbjava1_8.Mysql_class.Service.ItemService;

import com.example.fwwbjava1_8.Mysql_class.model.Item;
import com.example.fwwbjava1_8.Mysql_class.model.ItemResp;
import com.example.fwwbjava1_8.Mysql_class.model.ResultData;
import com.example.fwwbjava1_8.Mysql_class.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private AuthService authService;
    @Autowired
    private AdminService adminService;
    @Autowired
    Cache cache;

    @RequestMapping("/api/shop/itemlist")
    public ResultData getItemList(@CookieValue("token") String token) throws InvocationTargetException, IllegalAccessException {
        User user = authService.SearchUserByToken(token);
        if (user!=null){
            int userId = user.getId();
            if(authService.IsTokenExpired(userId)){
                return ResultData.fail(500,"token过期");
            }
        }else if (adminService.SearchToken(token) == null) {
            return ResultData.fail(403,"管理员不存在");
        }
        return ResultData.success(cache.getItemList());

    }

    @GetMapping("/api/shop/item")
    public ResultData getItem(@CookieValue("token") String token, @RequestParam int id){
        User user = authService.SearchUserByToken(token);
        if (user!=null){
            int userId = user.getId();
            if(authService.IsTokenExpired(userId)){
                return ResultData.fail(500,"token过期");
            }
        }else if (adminService.SearchToken(token) == null) {
            return ResultData.fail(403,"管理员不存在");
        }
        ItemResp resp = new ItemResp(itemService.getItem(id));
        return ResultData.success(resp);
    }

    //authService更改为adminService，只有admin才有权限添加item
    @PostMapping(value="/api/admin/additem")
    public ResultData addItem(@CookieValue("token") String token, Item item) {
        if (adminService.SearchToken(token)==null){
            return ResultData.fail(403,"管理员不存在");
        }
        return ResultData.success(itemService.AddItem(item));
    }

    @PostMapping(value="/api/admin/deleteitem")
    public ResultData deleteItem(@CookieValue("token") String token, int id) {
        if (adminService.SearchToken(token)==null){
            return ResultData.fail(403,"管理员不存在");
        }
        return ResultData.success(itemService.DeleteItem(id));
    }

    @PostMapping(value = "/api/admin/changeitem")
    public ResultData changeItem(@CookieValue("token") String token, Item item) {
        if (adminService.SearchToken(token)==null){
            return ResultData.fail(403,"管理员不存在");
        }
        return ResultData.success(itemService.ChangeItem(item));
    }
}