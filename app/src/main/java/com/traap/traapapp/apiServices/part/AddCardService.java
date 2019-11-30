package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.card.Result;
import com.traap.traapapp.apiServices.model.card.addCard.request.AddCardRequest;

public class AddCardService extends BasePart
{

    public AddCardService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void addCardService(AddCardRequest request, OnServiceStatus<WebServiceClass<Result>> listener)
    {
        start(getServiceGenerator().createService().addCard(request), listener);
    }
}
