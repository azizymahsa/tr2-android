package ir.traap.tractor.android.ui.fragments.ticket.rulesStadium;

import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.stadium_rules.ResponseStadiumRules;

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
