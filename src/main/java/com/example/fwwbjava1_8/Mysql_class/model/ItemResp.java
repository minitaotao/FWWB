package com.example.fwwbjava1_8.Mysql_class.model;

public class ItemResp extends Item{

    public ItemResp(Item item){
        this.setBeginDate(item.getBeginDate());
        this.setDescription(item.getDescription());
        this.setDetail(item.getDetail());
        this.setPrice(item.getPrice());
        this.setId(item.getId());
        this.setEndDate(item.getEndDate());
        this.setStock(item.getStock());
        this.setName(item.getName());
        this.setTotalstock(item.getTotalstock());
        this.setRule_age(item.getRule_age());
        this.setRule_income(item.getRule_income());
        this.setPurlimnum(item.getPurlimnum());
    }
}
