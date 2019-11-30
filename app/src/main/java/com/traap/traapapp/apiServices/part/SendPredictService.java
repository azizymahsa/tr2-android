package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.predict.sendPredict.request.SendPredictRequest;

public class SendPredictService extends BasePart
{

    public SendPredictService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void sendPredictService(SendPredictRequest request, OnServiceStatus<WebServiceClass<Object>> listener)
    {
        start(getServiceGenerator().createService().sendPredict(request), listener);
    }
}
