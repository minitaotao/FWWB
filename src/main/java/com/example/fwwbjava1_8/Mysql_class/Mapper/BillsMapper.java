package com.example.fwwbjava1_8.Mysql_class.Mapper;

import com.example.fwwbjava1_8.Mysql_class.model.Bill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BillsMapper {
    @Insert("insert into bills(user_id,item_id,time,income,spending,surplus) values(#{user_id},#{item_id},#{date},#{income},#{spending},#{surplus})")
    int AddBill(Bill bill);
}
