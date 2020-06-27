package com.traap.traapapp.ui.fragments.performanceEvaluation.setEvaluation;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.setEvaluation.request.QuestionItemRequest;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.setEvaluation.request.SetPlayerEvaluationQuestionsRequest;
import com.traap.traapapp.utilities.Logger;

import java.util.List;

public class SetPlayerEvaluationImpl
{
    public static void SetPlayerEvaluation(List<QuestionItemRequest> questionItemRequest, onSetPlayerEvaluationListener listener)
    {
        SetPlayerEvaluationQuestionsRequest request = new SetPlayerEvaluationQuestionsRequest();
//        request.setMatchId(matchId);
//        request.setPlayerId(playerId);
        request.setQuestionRequestList(questionItemRequest);

        SingletonService.getInstance().getPerformanceEvaluationService().setPlayerEvaluation(request, new OnServiceStatus<WebServiceClass<Object>>()
        {
            @Override
            public void onReady(WebServiceClass<Object> response)
            {
                if (response == null || response.data == null)
                {
                    listener.onSetPlayerEvaluationError("خطا در دریافت اطلاعات از سرور!");
                    Logger.e("-setPlayerEvaluation-", "null");
                    return;
                }
                if (response.info.statusCode != 201)
                {
                    listener.onSetPlayerEvaluationError(response.info.message);
                }
                else
                {
                    listener.onSetPlayerEvaluationCompleted(response.info.message);
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onSetPlayerEvaluationError(message);
            }
        });

    }

    public interface onSetPlayerEvaluationListener
    {
        void onSetPlayerEvaluationCompleted(String message);

        void onSetPlayerEvaluationError(String message);
    }
}
