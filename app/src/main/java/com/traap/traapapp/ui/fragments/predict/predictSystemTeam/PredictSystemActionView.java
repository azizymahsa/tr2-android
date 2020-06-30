package com.traap.traapapp.ui.fragments.predict.predictSystemTeam;

import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main.Column;
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredict.PlayerItem;
import com.traap.traapapp.ui.base.BaseView;

import java.util.List;

public interface PredictSystemActionView
{
    void onSelectPlayerFromDialog(int positionId, PlayerItem playerItem, int rowPosition, int columnPosition);
}
