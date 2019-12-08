package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessResponse;

/**
 * Created by MahtabAzizi on 12/8/2019.
 */
public class GetBalancePasswordLessService  extends BasePart
{

    public GetBalancePasswordLessService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }


    public void GetBalancePasswordLessService(OnServiceStatus<WebServiceClass<GetBalancePasswordLessResponse>> listener, GetBalancePasswordLessRequest req)
    {
        start(getServiceGenerator().createService().getBalancePasswordLess(req), listener);
    }

}
