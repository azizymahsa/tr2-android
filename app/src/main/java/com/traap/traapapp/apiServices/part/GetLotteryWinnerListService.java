package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.lottery.GetLotteryWinnerListResponse;

public class GetLotteryWinnerListService extends BasePart
{

    public GetLotteryWinnerListService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getLotteryWinnerListService(Integer matchId, OnServiceStatus<WebServiceClass<GetLotteryWinnerListResponse>> listener)
    {
        start(getServiceGenerator().createService().getLotteryWinnerList(matchId), listener);
    }
}
