package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getHappyCardInfo.GetHappyCardInfoRequest;
import com.traap.traapapp.apiServices.model.getHappyCardInfo.response.GetHappyCardInfoResponse;

/**
 * Created by Javad.Abadi on 8/19/2018.
 */
public class GetHappyCardInfoService extends BasePart
{
    public GetHappyCardInfoService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void GetCardInfoService(OnServiceStatus<WebServiceClass<GetHappyCardInfoResponse>> listener, GetHappyCardInfoRequest req)
    {
        start(getServiceGenerator().createService().getHappyCardInfo(req), listener);
    }
}