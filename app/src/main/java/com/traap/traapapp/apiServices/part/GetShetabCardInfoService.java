package com.traap.traapapp.apiServices.part;


import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getShetabCardInfo.reponse.ShetabCardInfoResponse;
import com.traap.traapapp.apiServices.model.getShetabCardInfo.request.ShetabCardInfoRequest;

/**
 * Created by Javad.Abadi on 1/2/2019.
 */
public class GetShetabCardInfoService extends BasePart
{
    public GetShetabCardInfoService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getShetabCardInfo(OnServiceStatus<WebServiceClass<ShetabCardInfoResponse>> listener, ShetabCardInfoRequest req)
    {
        start(getServiceGenerator().createService().getShetabCardInfo(req), listener);
    }

}
