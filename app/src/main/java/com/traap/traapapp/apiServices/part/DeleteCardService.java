package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;

public class DeleteCardService extends BasePart
{

    public DeleteCardService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void deleteCardService(Integer cardId, OnServiceStatus<WebServiceClass<Object>> listener)
    {
        start(getServiceGenerator().createService().deleteCard(cardId), listener);
    }
}
