package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeRequest;
import ir.trap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeResponse;

/**
 * Created by MahtabAzizi on 10/21/2019.
 */
public class BillCodePayCodeService extends BasePart
{
    public BillCodePayCodeService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getBillCodePayCodeService(OnServiceStatus<WebServiceClass<GetBillCodePayCodeResponse>> listener, GetBillCodePayCodeRequest request)
    {
        start(getServiceGenerator().createService().getBillCodePayCode(request), listener);
    }
}
