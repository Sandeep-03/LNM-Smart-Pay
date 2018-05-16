package com.example.sandeep.lnmsmartpay;

/**
 * Created by Sandeep on 4/23/2018.
 */

public class Request {
    private String name,des,date,uid;
    private long amount,num;

    public Request() {
    }

    public Request(String name, String des, String date,String uid,long amount,long num) {
        this.name = name;
        this.num=num;
        this.des = des;
        this.date = date;
        this.uid=uid;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getNum() {
        return num;
    }
    public void setNum(long num) {
        this.num = num;
    }

}

