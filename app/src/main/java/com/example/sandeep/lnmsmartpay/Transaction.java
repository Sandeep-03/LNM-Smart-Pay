package com.example.sandeep.lnmsmartpay;

/**
 * Created by Sandeep on 4/23/2018.
 */

public class Transaction {
    private String name,des,date,type;
    private long amount;

    public Transaction() {
    }

    public Transaction(String name, String des, String date,String type,long amount) {
        this.name = name;
        this.des = des;
        this.date = date;
        this.type=type;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}

