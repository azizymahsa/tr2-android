package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main.PerformanceEvaluationMainResponse;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.playerSubstitution.request.PlayerSubstitutionRequest;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.playerSubstitution.response.PerformanceEvaluationPlayerSubstitutionResponse;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.getEvaluationQuestion.GetPlayerEvaluationQuestionResponse;

import java.util.List;

/**
 * Created by JavadAbadi on 2/23/2019.
 */
public class PerformanceEvaluationService extends BasePart
{
    public PerformanceEvaluationService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getMainEvaluation(Integer matchId, OnServiceStatus<WebServiceClass<PerformanceEvaluationMainResponse>> response)
    {
        start(getServiceGenerator().createService().getMainEvaluation(matchId), response);
    }

    public void getEvaluationSubstitution(Integer matchId, PlayerSubstitutionRequest request,
                                          OnServiceStatus<WebServiceClass<List<PerformanceEvaluationPlayerSubstitutionResponse>>> response)
    {
        start(getServiceGenerator().createService().getEvaluationSubstitution(matchId, request), response);
    }

    public void getPlayerEvaluationQuestion(Integer matchId, OnServiceStatus<WebServiceClass<List<GetPlayerEvaluationQuestionResponse>>> response)
    {
        start(getServiceGenerator().createService().setPlayerEvaluation(matchId), response);
    }
//
//    public void setPlayerEvaluation(Integer matchId, OnServiceStatus<WebServiceClass<List<SetPerformanceEvaluationPlayerResponse>>> response)
//    {
//        start(getServiceGenerator().createService().setPlayerEvaluation(matchId), response);
//    }

}
