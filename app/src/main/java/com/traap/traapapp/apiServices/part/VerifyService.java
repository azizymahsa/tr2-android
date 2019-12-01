package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.verify.VerifyRequest;
import com.traap.traapapp.apiServices.model.verify.VerifyResponse;

/**
 * Created by MahtabAzizi on 10/16/2019.
 */
public class VerifyService extends BasePart {
    public VerifyService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void verify(OnServiceStatus<WebServiceClass<VerifyResponse>> listener, VerifyRequest request)
    {
        start(getServiceGenerator().createService().verify(request), listener);
    }
}
