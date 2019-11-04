package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import ir.traap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;

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
