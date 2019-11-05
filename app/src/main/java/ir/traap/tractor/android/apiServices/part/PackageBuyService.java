package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.buyPackage.request.PackageBuyRequest;
import ir.traap.tractor.android.apiServices.model.buyPackage.response.PackageBuyResponse;

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
