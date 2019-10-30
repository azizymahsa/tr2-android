package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeRequest;
import ir.trap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeResponse;
import ir.trap.tractor.android.apiServices.model.getVersion.request.GetVersionRequest;
import ir.trap.tractor.android.apiServices.model.getVersion.response.GetVersionResponse;

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
