package com.traap.traapapp.ui.fragments.predict;

import com.traap.traapapp.apiServices.model.lottery.Winner;

import java.util.List;

public interface PredictActionView
{
    void showAlertFailure(String message);

    void onShowDetailWinnerList(List<Winner> winnerList);
}
