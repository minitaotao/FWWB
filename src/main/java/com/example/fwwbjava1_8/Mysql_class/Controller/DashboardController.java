package com.example.fwwbjava1_8.Mysql_class.Controller;

import com.example.fwwbjava1_8.Mysql_class.Service.DashboardService;
import com.example.fwwbjava1_8.Mysql_class.Service.AuthService;
import com.example.fwwbjava1_8.Mysql_class.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DashboardController {
    @Autowired
    private AuthService authService;
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/api/dashboard/orderlist")
    public ResultData getMyList(@CookieValue("token") String token) {
        User user = authService.SearchUserByToken(token);
        if (user==null){
            return ResultData.fail(403,"用户不存在");
        }
        int userId = user.getId();
        if(authService.IsTokenExpired(userId))
            return ResultData.fail(500,"token过期");
        else {
            return ResultData.success(dashboardService.getMyList(user.getId()));
        }
    }

    @GetMapping("/api/dashboard/order")
    public ResultData getOrder(@CookieValue("token") String token,@RequestParam String order_number) {
        User user = authService.SearchUserByToken(token);
        if (user==null ){
            return ResultData.fail(403,"用户不存在");
        }
        int userId = user.getId();
        if(authService.IsTokenExpired(userId))
            return ResultData.fail(500,"token过期");
        else{
            return ResultData.success(dashboardService.getOrder(order_number));
        }
    }
}




