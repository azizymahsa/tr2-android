package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.spectatorInfo.SpectatorInfoResponse;

/**
 * Created by MahtabAzizi on 2/9/2020.
 */
public class SpectatorInfoService extends BasePart
{

    public SpectatorInfoService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getSpectatorInfo(String nationalCode , OnServiceStatus<WebServiceClass<SpectatorInfoResponse>> listener)
    {
        start(getServiceGenerator().createService().getSpectatorInfo(nationalCode ), listener);
    }
}
