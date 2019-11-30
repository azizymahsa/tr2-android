package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.shetacChangePass2.request.ShetacChangePass2Request;

public class SheatcChangePassService extends BasePart
{

    public SheatcChangePassService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void sheatcChangePassService(ShetacChangePass2Request request, OnServiceStatus<WebServiceClass<Object>> listener)
    {
        start(getServiceGenerator().createService().doChangePass(request), listener);
    }
}
