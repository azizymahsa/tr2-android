package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyPackage.request.PackageBuyRequest;
import com.traap.traapapp.apiServices.model.buyPackage.response.PackageBuyResponse;

/**
 * Created by Javad.Abadi on 8/25/2018.
 */
public class PackageBuyService extends BasePart
{
    public PackageBuyService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void MciPackageBuyService(OnServiceStatus<WebServiceClass<PackageBuyResponse>> listener, PackageBuyRequest req)
    {
        start(getServiceGenerator().createService().buySimcardPackage(req), listener);
    }
}
