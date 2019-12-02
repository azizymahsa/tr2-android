package com.traap.traapapp.ui.adapters.matchSchedule;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.ui.fragments.matchSchedule.leaguse.LeagueTableFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.matchSchedule.NextMatchesFragment;
import com.traap.traapapp.ui.fragments.matchSchedule.PastMatchesFragment;

/**
 * Created by MahtabAzizi on 11/16/2019.
 */
public class MatchScheduleAdapter  extends FragmentStatePagerAdapter
{

    private final MainActionView mainActionView;
    private  List<MatchItem> nextMatchesList=new ArrayList<>();
    private  List<MatchItem> pastMatchesList=new ArrayList<>();
    private int numTabs;



    public MatchScheduleAdapter(FragmentManager fm, int numTabs, MainActionView mainActionView,
                                List<MatchItem> nextMatchesList, List<MatchItem> pastMatchesList)
    {
        super(fm);
        this.numTabs = numTabs;

        this.mainActionView = mainActionView;
        this.nextMatchesList = nextMatchesList;
        this.pastMatchesList = pastMatchesList;

    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 2:
                LeagueTableFragment tab1 = LeagueTableFragment.newInstance(mainActionView);

                return tab1;
            case 0:
                NextMatchesFragment tab3 = NextMatchesFragment.newInstance(nextMatchesList,mainActionView);

                return tab3;

            case 1:
                PastMatchesFragment tab2 = PastMatchesFragment.newInstance(pastMatchesList,mainActionView);

                return tab2;

            default:
                throw new RuntimeException("Tab position invalid " + position);
        }
    }



    @Override
    public int getCount()
    {
        return numTabs;
    }



}

