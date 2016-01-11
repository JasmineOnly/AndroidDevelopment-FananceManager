package cmu.finance;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Pedro on 22/11/2015.
 * Adapter of the ViewPager used on DetailViewPagerActivity.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position){
        return DetailViewPagerFragment.newInstance(position);
    }

    @Override
    public int getCount(){
        return 3; //number of pagers fragments
    }
}
