package com.example.fwwbjava1_8.Mysql_class.model;

import lombok.Data;

@Data
public class User {

    private String name;
    private String password;
    private String id_card;
    private long phone_num;
    private int id;
    private int balance;
    private int age;
    private int income;
    private String psy;
    private String education;
    private int defaulter;
    private int working_status;

    public int getWorking_status() {
        return working_status;
    }

    public void setWorking_status(Integer working_status) {
        this.working_status = working_status;
    }

    public int getDefaulter() {
        return defaulter;
    }

    public void setDefaulter(Integer defaulter) {
        this.defaulter = defaulter;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPsy() {
        return psy;
    }

    public void setPsy(String psy) {
        this.psy = psy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(Long phone_num) {
        this.phone_num = phone_num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

}
