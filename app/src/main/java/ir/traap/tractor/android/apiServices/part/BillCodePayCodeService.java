package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeRequest;
import ir.traap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeResponse;

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
