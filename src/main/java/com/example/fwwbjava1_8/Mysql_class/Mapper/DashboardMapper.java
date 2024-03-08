package com.example.fwwbjava1_8.Mysql_class.Mapper;

import com.example.fwwbjava1_8.Mysql_class.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DashboardMapper {
    @Select("select * from `orders` where buyer_id = #{id}")
    List<Order> getMyList(int id);
    @Select("select * from `orders` where order_number = #{order_number}")
    Order getOrder(String order_number);
}
