package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import com.traap.traapapp.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;

/**
 * Created by Javad.Abadi on 8/25/2018.
 */
public class GetPackageIrancellService extends BasePart
{
    public GetPackageIrancellService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void GetPackageIrancellService(OnServiceStatus<WebServiceClass<GetPackageIrancellResponse>> listener, GetPackageMciRequest userId)
    {
        start(getServiceGenerator().createService().getIrancellPackage(userId), listener);
    }

}
