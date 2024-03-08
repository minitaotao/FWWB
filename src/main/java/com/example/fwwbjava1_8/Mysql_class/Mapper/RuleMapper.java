package com.example.fwwbjava1_8.Mysql_class.Mapper;

import com.example.fwwbjava1_8.Mysql_class.model.RuleAge;
import com.example.fwwbjava1_8.Mysql_class.model.RuleIncome;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RuleMapper {

    @Select("select * from rule_age")
    List<RuleAge> getRule_ageList();

    @Select("select * from rule_income")
    List<RuleIncome> getRule_incomeList();

    @Insert("insert into rule_age(age_max,age_min,name,type) values(#{age_max},#{age_min},#{name},'年龄')")
    void AddRule_age(int age_max,int age_min,String name);

    @Insert("insert into rule_income(income_min,name,type) values(#{income_min},#{name},'收入')")
    void AddRule_income(int income_min,String name);

    @Select("select * from rule_age where id=#{id}")
    RuleAge getAge(Integer id);

    @Select("select * from rule_income where id=#{id}")
    RuleIncome getIncome(Integer id);
}
