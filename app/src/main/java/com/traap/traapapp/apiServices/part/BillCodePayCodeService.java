package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getBillCodePayCode.GetBillCodePayCodeRequest;
import com.traap.traapapp.apiServices.model.getBillCodePayCode.GetBillCodePayCodeResponse;

/**
 * Created by MahtabAzizi on 10/21/2019.
 */
public class BillCodePayCodeService extends BasePart
{
    public BillCodePayCodeService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getBillCodePayCodeService(OnServiceStatus<WebServiceClass<GetBillCodePayCodeResponse>> listener, GetBillCodePayCodeRequest request)
    {
        start(getServiceGenerator().createService().getBillCodePayCode(request), listener);
    }
}
