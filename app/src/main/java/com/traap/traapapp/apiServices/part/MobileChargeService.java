package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.availableAmount.AvailableAmounResponse;
import com.traap.traapapp.apiServices.model.mobileCharge.request.MobileChargeRequest;
import com.traap.traapapp.apiServices.model.mobileCharge.response.MobileChargeResponse;

/**
 * Created by Javad.Abadi on 7/25/2018.
 */
public class MobileChargeService extends BasePart
{
    public MobileChargeService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void MobileChargeService(OnServiceStatus<WebServiceClass<MobileChargeResponse>> listener, MobileChargeRequest req)
    {
        start(getServiceGenerator().createService().getMobileCharge(req), listener);
    }

    public void getAvailableAmount(OnServiceStatus<WebServiceClass<AvailableAmounResponse>> listener)
    {
        start(getServiceGenerator().createService().getAvailableAmount(), listener);
    }
}




