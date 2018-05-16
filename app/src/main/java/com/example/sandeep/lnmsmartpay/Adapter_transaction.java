package com.example.sandeep.lnmsmartpay;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Sandeep on 12/5/2017.
 */

public class Adapter_transaction extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public Adapter_transaction(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                T_send tab1 = new T_send();
                return tab1;
            case 1:
                T_request tab2 = new T_request();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
