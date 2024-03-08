package com.example.fwwbjava1_8.Mysql_class.model;

public class Item {

    private Integer price;
    private Integer id;
    private String name;
    private Integer stock;
    private Integer totalstock;
    private String description;
    private String detail;
    private Long beginDate;
    private Long endDate;
    private Integer purlimnum;
    private Integer rule_age;
    private Integer rule_income;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getTotalstock() {
        return totalstock;
    }

    public void setTotalstock(Integer totalstock) {
        this.totalstock = totalstock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Integer getPurlimnum() {
        return purlimnum;
    }

    public void setPurlimnum(Integer purlimnum) {
        this.purlimnum = purlimnum;
    }

    public Integer getRule_age() {
        return rule_age;
    }

    public void setRule_age(Integer rule_age) {
        this.rule_age = rule_age;
    }

    public Integer getRule_income() {
        return rule_income;
    }

    public void setRule_income(Integer rule_income) {
        this.rule_income = rule_income;
    }


}
