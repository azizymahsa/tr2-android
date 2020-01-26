package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.card.Result;
import com.traap.traapapp.apiServices.model.card.addCard.request.AddCardRequest;
import com.traap.traapapp.apiServices.model.setting.SettingResponse;

public class GetSettingService extends BasePart
{

    public GetSettingService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getSettingService(OnServiceStatus<WebServiceClass<SettingResponse>> listener)
    {
        start(getServiceGenerator().createService().getSetting(), listener);
    }
}
