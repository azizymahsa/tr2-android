package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getTicketInfo.GetTicketInfoRequest;
import com.traap.traapapp.apiServices.model.getTicketInfo.GetTicketInfoResponse;

/**
 * Created by MahtabAzizi on 11/6/2019.
 */
public class GetTicketInfoService extends BasePart
{
    public GetTicketInfoService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getTicketInfoService(OnServiceStatus<WebServiceClass<GetTicketInfoResponse>> listener, GetTicketInfoRequest request)
    {
        start(getServiceGenerator().createService().getTicketInfo(request), listener);
    }
}
