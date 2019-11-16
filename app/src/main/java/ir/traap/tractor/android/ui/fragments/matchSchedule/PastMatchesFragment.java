package ir.traap.tractor.android.ui.fragments.matchSchedule;

import android.os.Bundle;

import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;

/**
 * Created by MahtabAzizi on 11/16/2019.
 */
public class PastMatchesFragment extends BaseFragment
{
    private MainActionView mainActionView;

    public static PastMatchesFragment newInstance(String tab2, MatchScheduleFragment matchScheduleFragment, MainActionView mainActionView)
    {

        PastMatchesFragment fragment = new PastMatchesFragment();
        Bundle args = new Bundle();


        fragment.setMainView(mainActionView);
        return fragment;
    }
    private void setMainView(MainActionView mainActionView)
    {
        this.mainActionView = mainActionView;
    }

}
