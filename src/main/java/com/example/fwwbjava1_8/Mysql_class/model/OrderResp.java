package com.example.fwwbjava1_8.Mysql_class.model;

public class OrderResp {
    private long date;//购买时间

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public OrderResp(Order order, Item item){
        this.orderNumber = order.getOrder_number();
        this.date=order.getDate();
        this.itemId = item.getId();
        this.price = item.getPrice();
        this.name = item.getName();
        this.description = item.getDescription();
        this.detail = item.getDetail();
        this.count = order.getItem_quantity();
    }


    private String orderNumber;
    private int itemId;
    private int price;
    private String name;
    private String description;
    private String detail;
    private Integer count;

}
