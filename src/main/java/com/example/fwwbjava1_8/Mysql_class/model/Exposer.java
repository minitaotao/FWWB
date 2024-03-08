package com.example.fwwbjava1_8.Mysql_class.model;

public class Exposer {
    //加密措施
    private String md5;


    public Exposer(String md5) {
        this.md5=md5;
    }

    //其中必要字段，如是否开启秒杀，时间等省

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
