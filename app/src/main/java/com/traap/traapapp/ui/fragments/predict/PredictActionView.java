package com.traap.traapapp.ui.fragments.predict;

import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.ui.base.BaseView;

import java.util.List;

public interface PredictActionView extends BaseView
{
    void backToMainFragment();

    void onShowLast5PastMatch(Integer teamLiveScoreId);

    void onPredictLeagueTable(Integer teamId, Integer matchId, Boolean isPredictable);

    void onSetPredictCompleted(Integer matchIdt, Boolean isPredictable, Boolean isFormationPredict, String message);

    void onShowDetailWinnerList(List<Winner> winnerList);
}
