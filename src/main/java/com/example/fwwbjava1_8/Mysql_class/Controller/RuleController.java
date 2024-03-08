package com.example.fwwbjava1_8.Mysql_class.Controller;

import com.example.fwwbjava1_8.Mysql_class.Service.AdminService;
import com.example.fwwbjava1_8.Mysql_class.Service.RuleService;
import com.example.fwwbjava1_8.Mysql_class.model.ResultData;
import com.example.fwwbjava1_8.Mysql_class.model.RuleAge;
import com.example.fwwbjava1_8.Mysql_class.model.RuleIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class RuleController {

    @Autowired
    private RuleService ruleService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/api/admin/rule/add")
    public ResultData AddRule(@RequestBody Map<String,Object> map){
        String Type = (String) map.get("RuleType");
        if(Type.equals("age")){
            int age_min = (int)map.get("min");
            int age_max = (int)map.get("max");
            String age_name = (String)map.get("name");
            return ResultData.success(ruleService.AddRule_age(age_max,age_min,age_name));
        }else if(Type.equals("income")){
            int income_min = (int) map.get("min");
            String income_name = (String) map.get("name");
            return ResultData.success(ruleService.AddRule_income(income_min,income_name));
        }else
            return null;
    }

    @GetMapping(value = "/api/admin/rule/list")
    public ResultData getRuleList(@CookieValue("token") String token,@RequestParam(value = "type",defaultValue = "null") String type) {
        List<RuleIncome> list_income = ruleService.Rule_incomeList();
        List<RuleAge> list_age = ruleService.Rule_ageList();
        ArrayList<Object> list_rule = new ArrayList<>();
        list_rule.addAll(list_age);
        list_rule.addAll(list_income);
        if (adminService.SearchToken(token) == null) {
            return ResultData.fail(403,"管理员不存在");
        } else {
            if (Objects.equals(type, "income"))
                return ResultData.success(list_income);
            else if (Objects.equals(type, "age"))
                return ResultData.success(list_age);
            else
                return ResultData.success(list_rule);
        }
    }
}
