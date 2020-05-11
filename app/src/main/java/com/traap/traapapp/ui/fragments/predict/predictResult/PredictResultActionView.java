package com.traap.traapapp.ui.fragments.predict.predictResult;

import com.traap.traapapp.apiServices.model.lottery.Winner;

import java.util.List;

public interface PredictResultActionView
{
    void showAlertFailure(String message);

    void onShowDetailWinnerList(List<Winner> winnerList);
}
