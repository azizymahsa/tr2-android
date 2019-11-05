package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getVersion.request.GetVersionRequest;
import ir.traap.tractor.android.apiServices.model.getVersion.response.GetVersionResponse;

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
