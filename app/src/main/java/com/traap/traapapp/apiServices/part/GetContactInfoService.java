package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.contactInfo.GetContactInfoRequest;
import com.traap.traapapp.apiServices.model.contactInfo.GetContactInfoResponse;
import com.traap.traapapp.apiServices.model.getMyBill.GetMyBillRequest;
import com.traap.traapapp.apiServices.model.getMyBill.GetMyBillResponse;

/**
 * Created by MahtabAzizi on 12/7/2019.
 */
public class GetContactInfoService extends BasePart
{
    public GetContactInfoService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getContactInfoService(OnServiceStatus<WebServiceClass<GetContactInfoResponse>> listener, GetContactInfoRequest request)
    {
        start(getServiceGenerator().createService().getContactInfo(), listener);
    }
}
