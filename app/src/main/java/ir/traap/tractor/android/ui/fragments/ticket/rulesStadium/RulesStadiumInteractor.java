package ir.traap.tractor.android.ui.fragments.ticket.rulesStadium;

import ir.traap.tractor.android.apiServices.model.stadium_rules.ResponseStadiumRules;

public interface RulesStadiumInteractor
{
    void rulesStadiumRequest(OnFinishedRulesStadiumListener listener, Integer  ruls);


    interface OnFinishedRulesStadiumListener {
        void onFinishedStadiumRules(ResponseStadiumRules response);
        void onErrorStadiumRules(String error);
    }
}
