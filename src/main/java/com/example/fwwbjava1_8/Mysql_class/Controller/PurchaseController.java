package com.example.fwwbjava1_8.Mysql_class.Controller;

import com.example.fwwbjava1_8.Mysql_class.Mapper.UserMapper;
import com.example.fwwbjava1_8.Mysql_class.Service.PurchaseService;
import com.example.fwwbjava1_8.Mysql_class.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

    @Autowired
    public PurchaseService purchaseService;
    @Autowired
    UserMapper userMapper;

    @PostMapping(value = "/api/shop/addOrder")
    public ResultData purchaseresp(Order order){
        return ResultData.success(purchaseService.AddOrderANDBill(order.getItem_id(),order.getBuyer_id(), order.getItem_quantity()));
    }
}
