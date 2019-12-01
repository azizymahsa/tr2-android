package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getVersion.request.GetVersionRequest;
import com.traap.traapapp.apiServices.model.getVersion.response.GetVersionResponse;

/**
 * Created by Javad.Abadi on 10/21/2019.
 */
public class GetVersionService extends BasePart
{
    public GetVersionService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getVersionService(OnServiceStatus<WebServiceClass<GetVersionResponse>> listener, GetVersionRequest request)
    {
        start(getServiceGenerator().createService().getVersion(request), listener);
    }
}
