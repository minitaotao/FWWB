package com.example.fwwbjava1_8.Mysql_class.Service;

import com.example.fwwbjava1_8.Mysql_class.Mapper.DashboardMapper;
import com.example.fwwbjava1_8.Mysql_class.Mapper.ItemMapper;
import com.example.fwwbjava1_8.Mysql_class.model.Item;
import com.example.fwwbjava1_8.Mysql_class.model.Order;
import com.example.fwwbjava1_8.Mysql_class.model.OrderResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Repository
public class DashboardService {
    @Autowired
    private DashboardMapper dashboardMapper;
    @Autowired
    private ItemMapper itemMapper;

    public List<OrderResp> getMyList(int id){
            List<Order> orders = dashboardMapper.getMyList(id);
            List<OrderResp> orderResps = new LinkedList<>();
            orders.forEach((order)->{
                Item item = itemMapper.getItem(order.getItem_id());
                OrderResp orderResp = new OrderResp(order,item);
                orderResps.add(orderResp);
            });
            return orderResps;
    }

    public OrderResp getOrder(String order_number){
        int itemId = dashboardMapper.getOrder(order_number).getItem_id();
        Item item = itemMapper.getItem(itemId);
        Order order = dashboardMapper.getOrder(order_number);
        return new OrderResp(order,item);
    }
}
