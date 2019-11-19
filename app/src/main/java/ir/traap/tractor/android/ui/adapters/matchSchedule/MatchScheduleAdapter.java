package ir.traap.tractor.android.ui.adapters.matchSchedule;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.traap.tractor.android.ui.fragments.leaguse.LeagueTableFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.fragments.matchSchedule.MatchScheduleFragment;
import ir.traap.tractor.android.ui.fragments.matchSchedule.NextMatchesFragment;
import ir.traap.tractor.android.ui.fragments.matchSchedule.PastMatchesFragment;
import ir.traap.tractor.android.ui.fragments.ticket.BuyTicketsFragment;
import ir.traap.tractor.android.ui.fragments.ticket.CompeletInfoFragment;
import ir.traap.tractor.android.ui.fragments.ticket.SelectPositionFragment;
import ir.traap.tractor.android.ui.fragments.ticket.ShowTicketsFragment;

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
                PastMatchesFragment tab2 = PastMatchesFragment.newInstance(pastMatchesList,mainActionView);

                return tab2;

            case 1:
                NextMatchesFragment tab3 = NextMatchesFragment.newInstance(nextMatchesList,mainActionView);

                return tab3;

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

