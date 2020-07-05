package com.traap.traapapp.ui.fragments.predict.predictSystemTeam;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getSystem.request.GetPredictSystemFromIdRequest;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getSystem.response.GetPredictSystemFromIdResponse;
import com.traap.traapapp.utilities.Logger;

import java.util.List;

public class PredictGetFormationContentImpl
{
    public static void GetFormationContent(Integer matchId, int formationId, onGetFormationContentListener listener)
    {
        GetPredictSystemFromIdRequest request = new GetPredictSystemFromIdRequest(formationId);

        SingletonService.getInstance().getPredictService().getPredictSystemFromId(matchId, request, new OnServiceStatus<WebServiceClass<List<List<GetPredictSystemFromIdResponse>>>>()
        {
            @Override
            public void onReady(WebServiceClass<List<List<GetPredictSystemFromIdResponse>>> response)
            {
                if (response == null || response.data == null)
                {
                    listener.onGetFormationContentError("خطا در دریافت اطلاعات از سرور!");
                    Logger.e("-getPredictSystemFromId-", "null");
                    return;
                }
                if (response.info.statusCode != 200)
                {
                    listener.onGetFormationContentError(response.info.message);
                }
                else
                {
                    listener.onGetFormationContentCompleted(response.data);
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onGetFormationContentError(message);
            }
        });
    }

    public interface onGetFormationContentListener
    {
        void onGetFormationContentCompleted(List<List<GetPredictSystemFromIdResponse>> response);

        void onGetFormationContentError(String message);
    }
}
