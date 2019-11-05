package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import ir.traap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;

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
