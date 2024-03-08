package com.example.fwwbjava1_8.Mysql_class.model;

public class RegisterForm {
    public long phone_num;
    public String password;
    public String name;
    public String id_card;

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public long getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(long phone_num) {
        this.phone_num = phone_num;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
