package com.traap.traapapp.ui.fragments.leagueTable;

import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.ui.base.BaseView;

public interface LeagueTableActionView extends BaseView
{
    void backToPredictFragment(Integer matchId);

    void openPastResultFragment(LeagueTableParent parent, String matchId, Boolean isPredictable, String teamId, String imageLogo, String logoTitle);

    void onMatchResultFragment();

    void openDrawerLeagueTable();

    void closeDrawerLeagueTable();
}
