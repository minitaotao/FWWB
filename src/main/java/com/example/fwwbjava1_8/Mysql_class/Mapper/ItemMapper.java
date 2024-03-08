package com.example.fwwbjava1_8.Mysql_class.Mapper;

import com.example.fwwbjava1_8.Mysql_class.model.Item;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ItemMapper {

    @Select("select * from item")
    List<Item> getItemList();
    @Select("select * from item where id=#{id}")
    Item getItem(Integer id);
    @Insert("insert into item(name,price,beginDate,endDate,stock,totalstock,description,detail,rule_age,purlimnum,rule_income) values(#{name},#{price},#{beginDate},#{endDate},#{stock},#{totalstock},#{description},#{detail},#{rule_age},#{purlimnum},#{rule_income})")
    int AddItem(Item item);
    @Delete("delete from item where id=#{id}")
    int DeleteItem(int id);
    @Update("update item set stock = #{stock} where id=#{id}")
    int UpdateItemStock(Item item);
    @Update("update item set id=#{id},name=#{name},beginDate=#{beginDate},endDate=#{endDate},totalstock=#{totalstock},stock=#{stock},price=#{price},description=#{description},detail=#{detail},rule_age=#{rule_age},rule_income=#{rule_income},purlimnum=#{purlimnum} where id=#{id}")
    int ChangeItem(Item item);
}