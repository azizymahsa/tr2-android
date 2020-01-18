package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.predict.getPredict.response.GetPredictResponse;
import com.traap.traapapp.apiServices.model.predict.getPredict.response.GetPredictResponse2;
import com.traap.traapapp.apiServices.model.stadium_rules.ResponseStadiumRules;

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

    public void getRulsStadium(Integer id , OnServiceStatus<WebServiceClass<ResponseStadiumRules>> listener)
    {
        start(getServiceGenerator().createService().getRulsStadium(id ), listener);
    }
}
