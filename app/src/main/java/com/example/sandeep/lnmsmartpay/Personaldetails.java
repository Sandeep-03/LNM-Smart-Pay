package com.example.sandeep.lnmsmartpay;

/**
 * Created by Sandeep on 12/10/2017.
 */

public class Personaldetails {
    private String username;
    private String name;
    private String email;
    private Long phn_number;
    private Long balance;
    private Long tra_num;
    private Long req_num;

    public String getusername(){return username;}
    public String getname(){return name;}
    public String getemail(){return email;}
    public Long getphn_number(){return phn_number;}
    public Long getbalance(){return balance;}
    public Long getTra_num(){return tra_num;}
    public Long getReq_num(){return req_num;}

    public void setUsername(String username){this.username=username;}
    public void setName(String name){this.name=name;}
    public void setEmail(String email){this.email=email;}
    //public void setPhn_number(Long phn_number){this.phn_number=phn_number;}
    //public void setBalance(Long balance){this.balance=balance;}
}
