package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getMyBill.GetMyBillRequest;
import ir.trap.tractor.android.apiServices.model.getMyBill.GetMyBillResponse;

/**
 * Created by MahtabAzizi on 10/21/2019.
 */
public class GetMyBillsService extends BasePart
{
    public GetMyBillsService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getMyBills(OnServiceStatus<WebServiceClass<GetMyBillResponse>> listener, GetMyBillRequest request)
    {
        start(getServiceGenerator().createService().getMyBills(), listener);
    }
}
