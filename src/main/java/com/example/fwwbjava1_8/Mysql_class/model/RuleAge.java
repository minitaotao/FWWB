package com.example.fwwbjava1_8.Mysql_class.model;

public class RuleAge {
    private Integer id;
    private Integer age_max;
    private Integer age_min;
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

    public int getAge_max() {
        return age_max;
    }

    public void setAge_max(Integer age_max) {
        this.age_max = age_max;
    }

    public int getAge_min() {
        return age_min;
    }

    public void setAge_min(Integer age_min) {
        this.age_min = age_min;
    }
}
