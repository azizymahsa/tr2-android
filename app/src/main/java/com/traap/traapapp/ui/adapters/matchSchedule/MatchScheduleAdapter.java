package com.traap.traapapp.ui.adapters.matchSchedule;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.ui.fragments.leagueTable.LeagueTableActionView;
import com.traap.traapapp.ui.fragments.leagueTable.LeagueTableFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.matchSchedule.NextMatchesFragment;
import com.traap.traapapp.ui.fragments.matchSchedule.PastMatchesFragment;

/**
 * Created by MahtabAzizi on 11/16/2019.
 */
public class MatchScheduleAdapter extends FragmentStatePagerAdapter implements LeagueTableActionView
{

    private final MainActionView mainActionView;
    private List<MatchItem> nextMatchesList = new ArrayList<>();
    private List<MatchItem> pastMatchesList = new ArrayList<>();
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
            case 0:
            {
                NextMatchesFragment tab3 = NextMatchesFragment.newInstance(nextMatchesList, mainActionView);
                return tab3;
            }
            case 1:
            {
                PastMatchesFragment tab2 = PastMatchesFragment.newInstance(pastMatchesList, mainActionView);
                return tab2;
            }
            case 2:
            {
                LeagueTableFragment tab1 = LeagueTableFragment.newInstance(LeagueTableParent.MatchScheduleFragment, "0", false, TrapConfig.TRACTOR_LIVE_SCORE_ID, this);
                return tab1;
            }
            default:
                throw new RuntimeException("Tab position invalid " + position);
        }
    }


    @Override
    public int getCount()
    {
        return numTabs;
    }


    @Override
    public void backToPredictFragment(Integer matchId)
    {
//        mainActionView.onPredict(matchId);
    }

    @Override
    public void openPastResultFragment(LeagueTableParent parent, String matchId, Boolean isPredictable, String teamId, String imageLogo, String logoTitle)
    {
        mainActionView.openPastResultFragment(parent, matchId, isPredictable, teamId, imageLogo, logoTitle);
    }

    @Override
    public void onMatchResultFragment()
    {
//        mainActionView.
    }

    @Override
    public void openDrawerLeagueTable()
    {
        mainActionView.openDrawer();
    }

    @Override
    public void closeDrawerLeagueTable()
    {
        mainActionView.closeDrawer();
    }

    @Override
    public void showLoading()
    {
        mainActionView.showLoading();
    }

    @Override
    public void hideLoading()
    {
        mainActionView.hideLoading();
    }
}

