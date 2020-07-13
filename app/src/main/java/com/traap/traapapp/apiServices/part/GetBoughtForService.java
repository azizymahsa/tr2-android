package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getBoughtFor.GetBoughtForResponse;

/**
 * Created by MahtabAzizi on 12/29/2019.
 */
public class GetBoughtForService extends BasePart
{
    public GetBoughtForService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getBoughtFor_InCharge(OnServiceStatus<WebServiceClass<GetBoughtForResponse>> listener)
    {
        start(getServiceGenerator().createService().getBoughtFor_InCharge(), listener);
    }
}
