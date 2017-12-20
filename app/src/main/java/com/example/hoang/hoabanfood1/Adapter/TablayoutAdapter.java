package com.example.hoang.hoabanfood1.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hoang.hoabanfood1.Fragment.FirstFragment;
import com.example.hoang.hoabanfood1.Fragment.SecondFragment;
import com.example.hoang.hoabanfood1.Fragment.ThirdFragment;

/**
 * Created by hoang on 10/20/2017.
 */

public class TablayoutAdapter extends FragmentPagerAdapter {
    private String listtab[] = {"Sản phẩm mới","Sản phẩm giá rẻ","Sản phẩm cao cấp"};
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;

    public TablayoutAdapter(FragmentManager fm) {
        super(fm);
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
        {
            return firstFragment;
        }
        else if(position==1)
            return secondFragment;
        else if(position==2)
            return thirdFragment;
        return null ;
    }

    @Override
    public int getCount() {
        return listtab.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listtab[position];
    }
}
