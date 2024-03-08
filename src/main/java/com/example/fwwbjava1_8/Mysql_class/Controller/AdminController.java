package com.example.fwwbjava1_8.Mysql_class.Controller;


import com.example.fwwbjava1_8.Mysql_class.Mapper.UserMapper;
import com.example.fwwbjava1_8.Mysql_class.Mapper.OrderMapper;
import com.example.fwwbjava1_8.Mysql_class.Service.AdminService;
import com.example.fwwbjava1_8.Mysql_class.Service.AuthService;
import com.example.fwwbjava1_8.Mysql_class.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.UUID;

@RestController
@SessionAttributes
public class AdminController {
    @Autowired
    public AdminService adminService;
    @Autowired
    public AuthService authService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;

    @PostMapping(value = "/api/loginadmin")
    public Object login(LoginForm form,@CookieValue(required=false) String token) {

        Admin tname = adminService.AdminLogin(form.phone_num,form.password);

        //LoginResp resp = new LoginResp();
        if (tname == null) {
            //resp.status=-1;
            //resp.msg="auth fail";
            return ResultData.fail(403,"手机号或者密码错误");
        }

            //resp.status=0;
            //resp.msg="ok";
            String uuid = UUID.randomUUID().toString();

            adminService.AddToken(uuid,tname.getId());
            //resp.token = uuid;
        if (token==null){
            return ResponseEntity.ok().header("Set-Cookie","token"+"="+uuid).body(tname.getName());
        }

        return ResponseEntity.ok().header("Set-Cookie","token"+"="+token+";expires="+ new Date()).header("Set-Cookie","token"+"="+uuid).body(tname.getName());

    }


    @RequestMapping(method = RequestMethod.GET,value="/api/admin/orderlist")
    public ResponseEntity<List<Order>> orderList1(@CookieValue("token") String token,@RequestParam(value = "item_id",defaultValue = "-1") int item_id, @RequestParam(value = "beginDate",defaultValue = "0") long begindate, @RequestParam(value = "endDate",defaultValue = "2000000000") long enddate){
        if (adminService.SearchToken(token)==null){
            return ResponseEntity.status(403).body(null);
        }
        if(item_id==-1){
            return ResponseEntity.ok().body(orderMapper.getOrderListByDate(begindate,enddate));
        }
        else{
            return ResponseEntity.ok().body(orderMapper.getOrderList(item_id,begindate,enddate));
        }
    }

    @RequestMapping(method = RequestMethod.GET,value="/api/admin/bankbalance")
    public ResultData bankBalance(@CookieValue("token") String token){
        if (adminService.SearchToken(token)==null){
            return ResultData.fail(403,"管理员不存在");
        }
        return ResultData.success(userMapper.GetBankBalance());
    }
}
