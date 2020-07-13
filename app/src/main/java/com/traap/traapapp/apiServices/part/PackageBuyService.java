package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyPackage.request.PackageBuyRequest;
import com.traap.traapapp.apiServices.model.buyPackage.response.PackageBuyResponse;
import com.traap.traapapp.apiServices.model.getBoughtFor.GetBoughtForResponse;
import com.traap.traapapp.apiServices.model.getSimPackageList.request.GetSimPackageListRequest;
import com.traap.traapapp.apiServices.model.getSimPackageList.response.GetSimPackageListResponse;

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

    public void SimPackageBuyService(OnServiceStatus<WebServiceClass<PackageBuyResponse>> listener, PackageBuyRequest req)
    {
        start(getServiceGenerator().createService().buySimCardPackage(req), listener);
    }

    public void getSimCardPackageList(GetSimPackageListRequest request, OnServiceStatus<WebServiceClass<GetSimPackageListResponse>> listener)
    {
        start(getServiceGenerator().createService().getSimCardPackageList(request), listener);
    }

    public void getBoughtFor_InPackage(OnServiceStatus<WebServiceClass<GetBoughtForResponse>> listener)
    {
        start(getServiceGenerator().createService().getBoughtFor_InPackage(), listener);
    }

}
