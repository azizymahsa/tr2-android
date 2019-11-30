package com.traap.traapapp.ui.fragments.ticket.rulesStadium;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.stadium_rules.ResponseStadiumRules;

public class RulesStadiumImpl implements RulesStadiumInteractor
{


    @Override
    public void rulesStadiumRequest(OnFinishedRulesStadiumListener listener, Integer ruls)
    {
        SingletonService.getInstance().getPredictService().getRulsStadium(ruls, new OnServiceStatus<WebServiceClass<ResponseStadiumRules>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseStadiumRules> response)
            {
                try
                {
                    if (response.info.statusCode == 200)
                    {
                        listener.onFinishedStadiumRules(response.data);
                    }
                    else
                    {
                        listener.onErrorStadiumRules(response.info.message);
                    }
                }
                catch (Exception e)
                {

                    listener.onErrorStadiumRules(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorStadiumRules(message);

            }
        });

    }
}
