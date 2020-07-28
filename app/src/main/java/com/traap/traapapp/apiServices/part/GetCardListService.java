package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.card.getCardList.GetCardListResponse;

public class GetCardListService extends BasePart
{

    public GetCardListService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getCardList(OnServiceStatus<WebServiceClass<GetCardListResponse>> listener)
    {
        start(getServiceGenerator().createService().getCardList(), listener);
    }
}
