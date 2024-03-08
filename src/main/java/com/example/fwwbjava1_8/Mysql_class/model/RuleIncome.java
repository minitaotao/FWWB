package com.example.fwwbjava1_8.Mysql_class.model;

public class RuleIncome {
    private Integer id;
    private Integer income_min;
    private String name;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIncome_min() {
        return income_min;
    }

    public void setIncome_min(Integer income_min) {
        this.income_min = income_min;
    }
}
