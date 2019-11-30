package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import com.traap.traapapp.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;

/**
 * Created by Javad.Abadi on 8/14/2018.
 */
public class GetPackageMciService extends BasePart
{
    public GetPackageMciService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void GetPackageMciService(OnServiceStatus<WebServiceClass<GetPackageMciResponse>> listener, GetPackageMciRequest request)
    {
        start(getServiceGenerator().createService().getPackageMci(request), listener);
    }

}
