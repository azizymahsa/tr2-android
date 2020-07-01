package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.mySupportProfile.ResponseMySupport;
import com.traap.traapapp.apiServices.model.predict.predictResult.getMyPredict.MyPredictResponse;
import com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response.GetPredictResponse;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredict.GetMainPredictSystemResponse;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredictInDeActive.GetMainPredictSystemInDeActiveResponse;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getSystem.request.GetPredictSystemFromIdRequest;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getSystem.response.GetPredictSystemFromIdResponse;
import com.traap.traapapp.apiServices.model.predict.predictSystem.sendPredictPlayers.request.SendPredictSystemPlayersRequest;
import com.traap.traapapp.apiServices.model.stadium_rules.ResponseStadiumRules;

import java.util.List;

public class GetPredictService extends BasePart
{

    public GetPredictService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getPredictService(Integer matchId, OnServiceStatus<WebServiceClass<GetPredictResponse>> listener)
    {
        start(getServiceGenerator().createService().getPredict(matchId), listener);
    }

    public void getPredictEnableService(OnServiceStatus<WebServiceClass<MatchItem>> listener)
    {
        start(getServiceGenerator().createService().getPredictEnable(), listener);
    }

    public void getMyPredictService(OnServiceStatus<WebServiceClass<MyPredictResponse>> listener)
    {
        start(getServiceGenerator().createService().getMyPredict(), listener);
    }

    public void getMySupportsService(OnServiceStatus<WebServiceClass<ResponseMySupport>> listener)
    {
        start(getServiceGenerator().createService().getMySupports(), listener);
    }

    public void getRulesStadium(Integer id , OnServiceStatus<WebServiceClass<ResponseStadiumRules>> listener)
    {
        start(getServiceGenerator().createService().getRulsStadium(id), listener);
    }

    public void getMainPredictSystem(int matchId, OnServiceStatus<WebServiceClass<GetMainPredictSystemResponse>> listener)
    {
        start(getServiceGenerator().createService().getMainPredictSystem(matchId), listener);
    }

    public void getMainPredictSystemInDeActive(int matchId, OnServiceStatus<WebServiceClass<GetMainPredictSystemInDeActiveResponse>> listener)
    {
        start(getServiceGenerator().createService().getMainPredictSystemInDeActive(matchId), listener);
    }

    public void getPredictSystemFromId(Integer matchId, GetPredictSystemFromIdRequest request, OnServiceStatus<WebServiceClass<List<List<GetPredictSystemFromIdResponse>>>> listener)
    {
        start(getServiceGenerator().createService().getPredictSystemFromId(matchId, request), listener);
    }

    public void sendPredictSystemPlayers(Integer matchId, SendPredictSystemPlayersRequest request, OnServiceStatus<WebServiceClass<Object>> listener)
    {
        start(getServiceGenerator().createService().sendPredictSystemPlayers(matchId, request), listener);
    }

}
