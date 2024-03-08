package com.example.fwwbjava1_8.Mysql_class.Service;

import com.example.fwwbjava1_8.Mysql_class.Mapper.RuleMapper;
import com.example.fwwbjava1_8.Mysql_class.model.RuleAge;
import com.example.fwwbjava1_8.Mysql_class.model.RuleIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public class RuleService {
    @Autowired
    private RuleMapper ruleMapper;

    public String AddRule_age(int age_max, int age_min,String name) {
        ruleMapper.AddRule_age(age_max, age_min,name);
        if(age_max == -1){
            return "添加成功" + 1 + "条最小年龄规则";
        }else if(age_min == -1){
            return "添加成功" + 1 + "条最大年龄规则";
        }else {
            return "添加成功1条最大年龄规则和一条最小年龄规则";
        }
    }

    public String AddRule_income(int income_min,String name) {
        ruleMapper.AddRule_income(income_min,name);
        if(income_min == -1){
            return "添加成功无收入限制规则";
        }else {
            return "添加成功"+1+"条最小收入规则";
        }
    }

    public List<RuleAge> Rule_ageList(){
        return ruleMapper.getRule_ageList();
    }

    public List<RuleIncome> Rule_incomeList(){
        return ruleMapper.getRule_incomeList();
    }
}



























