package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.card.Result;
import com.traap.traapapp.apiServices.model.card.editCard.request.EditCardRequest;

public class EditCardService extends BasePart
{

    public EditCardService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void editCardService(Integer cardId, EditCardRequest request, OnServiceStatus<WebServiceClass<Result>> listener)
    {
        start(getServiceGenerator().createService().editCard(cardId, request), listener);
    }
}
