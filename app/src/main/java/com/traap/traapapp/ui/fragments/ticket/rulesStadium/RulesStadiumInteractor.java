package com.traap.traapapp.ui.fragments.ticket.rulesStadium;

import com.traap.traapapp.apiServices.model.stadium_rules.ResponseStadiumRules;

public interface RulesStadiumInteractor
{
    void rulesStadiumRequest(OnFinishedRulesStadiumListener listener, Integer  ruls);


    interface OnFinishedRulesStadiumListener {
        void onFinishedStadiumRules(ResponseStadiumRules response);
        void onErrorStadiumRules(String error);
    }
}
