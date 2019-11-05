package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getMyBill.GetMyBillRequest;
import ir.traap.tractor.android.apiServices.model.getMyBill.GetMyBillResponse;

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
