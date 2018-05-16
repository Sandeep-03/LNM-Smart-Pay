package com.example.sandeep.lnmsmartpay;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sandeep on 4/27/2018.
 */

public class Badgeform {
    String Name;

    public String letter(String name){
        name.trim();
        int x=0;
        String Letter;
        Letter = name.substring(0,1);
        return  Letter;

    }
}
