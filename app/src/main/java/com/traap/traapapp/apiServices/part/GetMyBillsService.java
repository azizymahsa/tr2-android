package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getMyBill.GetMyBillRequest;
import com.traap.traapapp.apiServices.model.getMyBill.GetMyBillResponse;

/**
 * Created by MahtabAzizi on 10/21/2019.
 */
public class GetMyBillsService extends BasePart
{
    public GetMyBillsService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getMyBills(OnServiceStatus<WebServiceClass<GetMyBillResponse>> listener, GetMyBillRequest request)
    {
        start(getServiceGenerator().createService().getMyBills(), listener);
    }
}
