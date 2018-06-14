package com.gmb.gmbrapideevalsal.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gmb.gmbrapideevalsal.HistoriqueFragment;
import com.gmb.gmbrapideevalsal.MainFragment;

/**
 * Created by GMB on 1/24/2018.
 */

public class PageAdapter  extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MainFragment tab1 = (MainFragment) MainFragment.newInstance("","");
                return tab1;
            case 3:
                MyTabFragment tab2 = (MyTabFragment) MyTabFragment.getInstance(2);
                return tab2;
            case 2:
                MyTabFragment tab3 = (MyTabFragment) MyTabFragment.getInstance(2);
                return tab3;

            case 1:
                HistoriqueFragment tab4 = (HistoriqueFragment) HistoriqueFragment.newInstance(HistoriqueFragment.TYPE_HISTO_HISTO, 1);
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
