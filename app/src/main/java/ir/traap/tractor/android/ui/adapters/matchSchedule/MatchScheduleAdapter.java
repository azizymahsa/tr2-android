package ir.traap.tractor.android.ui.adapters.matchSchedule;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import ir.traap.tractor.android.ui.fragments.leaguse.LeagueTableFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.fragments.matchSchedule.MatchScheduleFragment;
import ir.traap.tractor.android.ui.fragments.matchSchedule.NextMatchesFragment;
import ir.traap.tractor.android.ui.fragments.matchSchedule.PastMatchesFragment;

/**
 * Created by MahtabAzizi on 11/16/2019.
 */
public class MatchScheduleAdapter  extends FragmentStatePagerAdapter
{
    private final MatchScheduleFragment matchScheduleFragment;
    private final MainActionView mainActionView;
    private int numTabs;
    private LeagueTableFragment tabLeague;
    private PastMatchesFragment tabPastMatches;
    private NextMatchesFragment tabNextMatches;

    public MatchScheduleAdapter(FragmentManager fragmentManager, int tabCount, MatchScheduleFragment matchScheduleFragment, MainActionView mainActionView)
    {
        super(fragmentManager);
        this.numTabs = numTabs;

        this.matchScheduleFragment = matchScheduleFragment;
        this.mainActionView = mainActionView;

    }

    @Override
    public int getCount()
    {
        return numTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                tabNextMatches = NextMatchesFragment.newInstance("TAB1", matchScheduleFragment, mainActionView);

                return tabNextMatches;
            case 1:
                tabPastMatches = PastMatchesFragment.newInstance("TAB2", matchScheduleFragment, mainActionView);

                // createInstance();
                return tabPastMatches;
            case 2:
                tabLeague = LeagueTableFragment.newInstance("TAB3", matchScheduleFragment, mainActionView);

                // createInstance();
                return tabLeague;

            default:
                throw new RuntimeException("Tab position invalid " + position);
        }
    }

}
