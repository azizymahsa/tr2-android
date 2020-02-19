package com.traap.traapapp.ui.fragments.turnover;

import com.traap.traapapp.ui.fragments.main.MainActionView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by MahtabAzizi on 11/16/2019.
 */
public class TurnOverPagerAdapter extends FragmentStatePagerAdapter
{

    private final MainActionView mainActionView;


    private String tabTitles[] = new String[]{"برداشت", "واریز", "همه"};


    public TurnOverPagerAdapter(FragmentManager fm, MainActionView mainActionView)
    {
        super(fm);

        this.mainActionView = mainActionView;


    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 2:
                AllTurnoverFragment tab3 = AllTurnoverFragment.newInstance(mainActionView);

                return tab3;
            case 1:
                DepositTurnoverFragment tab2 = DepositTurnoverFragment.newInstance(mainActionView);

                return tab2;

            case 0:
                WithdrawTurnoverFragment tab1 = WithdrawTurnoverFragment.newInstance(mainActionView);

                return tab1;



            default:
                throw new RuntimeException("Tab position invalid " + position);
        }
    }



    @Override
    public int getCount()
    {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}

