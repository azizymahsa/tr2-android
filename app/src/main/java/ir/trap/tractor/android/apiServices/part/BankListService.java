package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getBankList.response.BankListResponse;
import ir.trap.tractor.android.apiServices.model.getMyBill.GetMyBillRequest;
import ir.trap.tractor.android.apiServices.model.getMyBill.GetMyBillResponse;

/**
 * Created by Javad.Abadi on 10/21/2019.
 */
public class BankListService extends BasePart
{
    public BankListService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getBankListService(OnServiceStatus<WebServiceClass<BankListResponse>> listener)
    {
        start(getServiceGenerator().createService().getBankList(), listener);
    }
}
