package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;

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

    public void GetPackageMciService(OnServiceStatus<GetPackageMciResponse> listener, GetPackageMciRequest request)
    {
        start(getServiceGenerator().createService().getPackageMci(request), listener);
    }

}
