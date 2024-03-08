package com.example.fwwbjava1_8.Mysql_class.model;

public class Bill {
    private int bills_id;
    private int user_id;
    private long date;
    private int item_id;
    private int income;
    private int spending;
    private int surplus;//对应账户余额

    public int getBills_id() {
        return bills_id;
    }

    public void setBills_id(int bills_id) {
        this.bills_id = bills_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getSpending() {
        return spending;
    }

    public void setSpending(int spending) {
        this.spending = spending;
    }

    public int getSurplus() {
        return surplus;
    }

    public void setSurplus(int surplus) {
        this.surplus = surplus;
    }
}
