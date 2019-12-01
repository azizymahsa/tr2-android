package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getBankList.response.BankListResponse;

/**
 * Created by Javad.Abadi on 10/21/2019.
 */
public class BankListService extends BasePart
{
    public BankListService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getBankListService(OnServiceStatus<WebServiceClass<BankListResponse>> listener)
    {
        start(getServiceGenerator().createService().getBankList(), listener);
    }
}
