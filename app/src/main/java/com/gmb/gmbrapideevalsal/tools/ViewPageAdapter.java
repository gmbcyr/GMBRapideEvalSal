package com.gmb.gmbrapideevalsal.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gmb.gmbrapideevalsal.HistoriqueFragment;
import com.gmb.gmbrapideevalsal.MainFragment;

/**
 * Created by GMB on 1/24/2018.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    private String title[] = {"Calculation", "History","Saves"};

    public ViewPageAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        //return MyTabFragment.getInstance(position);

        switch (position) {
            case 0:
                MainFragment tab1 = (MainFragment) MainFragment.newInstance("","");
                return tab1;
            /*case 2:
                ResultFragment tab2 = (ResultFragment) ResultFragment.newInstance("","");
                return tab2;*/
            case 2:
                HistoriqueFragment tab3 = (HistoriqueFragment) HistoriqueFragment.newInstance(HistoriqueFragment.TYPE_HISTO_SAVED, 1);
                return tab3;

            case 1:
                HistoriqueFragment tab4 = (HistoriqueFragment) HistoriqueFragment.newInstance(HistoriqueFragment.TYPE_HISTO_HISTO, 1);
                return tab4;

            default:
                return HistoriqueFragment.newInstance(HistoriqueFragment.TYPE_HISTO_SAVED, 1);
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
