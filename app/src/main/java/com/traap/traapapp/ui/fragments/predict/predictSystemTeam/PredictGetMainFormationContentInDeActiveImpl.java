package com.traap.traapapp.ui.fragments.predict.predictSystemTeam;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredictInDeActive.GetMainPredictSystemInDeActiveResponse;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getSystem.request.GetPredictSystemFromIdRequest;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getSystem.response.GetPredictSystemFromIdResponse;
import com.traap.traapapp.utilities.Logger;

import java.util.List;

public class PredictGetMainFormationContentInDeActiveImpl
{
    public static void GetFormationContentInDeActive(Integer matchId, onGetFormationContentInDeActiveListener listener)
    {
        SingletonService.getInstance().getPredictService().getMainPredictSystemInDeActive(matchId, new OnServiceStatus<WebServiceClass<GetMainPredictSystemInDeActiveResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetMainPredictSystemInDeActiveResponse> response)
            {
                if (response == null || response.data == null)
                {
                    listener.onGetFormationContentInDeActiveError("خطا در دریافت اطلاعات از سرور!");
                    Logger.e("-GetMainPredictSystemInDeActive-", "null");
                    return;
                }
                if (response.info.statusCode != 200)
                {
                    listener.onGetFormationContentInDeActiveError(response.info.message);
                }
                else
                {
                    listener.onGetFormationContentInDeActiveCompleted(response.data);
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onGetFormationContentInDeActiveError(message);
            }
        });
    }

    public interface onGetFormationContentInDeActiveListener
    {
        void onGetFormationContentInDeActiveCompleted(GetMainPredictSystemInDeActiveResponse response);

        void onGetFormationContentInDeActiveError(String message);
    }
}
