package com.example.fwwbjava1_8.Mysql_class.Mapper;

import com.example.fwwbjava1_8.Mysql_class.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrderMapper {

    @Select("select * from orders where id = #{order_id}")
    Order getOrder(int order_id);
    @Insert("insert into orders(order_number,item_id,buyer_id,date,item_quantity) values(#{order_number},#{item_id},#{buyer_id},#{date},#{item_quantity})")
    void AddOrder(Order order);
    @Select("select * from orders where buyer_id = #{buyerId} and item_id = #{itemId}")
    List<Order> getOrderListByUserIdAndItemId(int buyerId, int itemId);
    @Select("select * from orders where item_id = #{id}")
    List<Order> getOrderListByItemId(int id);
    @Select("select * from orders where date between #{beginDate} and #{endDate} ")
    List<Order> getOrderListByDate(long beginDate,long endDate);
    @Select("select * from orders where item_id = #{id} AND date between #{beginDate} and #{endDate}")
    List<Order> getOrderList(int id,long beginDate,long endDate);
    @Select("select * from orders")
    List<Order> getAllOrderList();

}
