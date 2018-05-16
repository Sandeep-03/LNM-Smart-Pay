package com.example.sandeep.lnmsmartpay;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Sandeep on 12/5/2017.
 */

public class Adapter_view extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public Adapter_view(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                View_all tab1 = new View_all();
                return tab1;
            case 1:
                View_payments tab2 = new View_payments();
                return tab2;
            case 2:
                View_purchase tab3 = new View_purchase();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
