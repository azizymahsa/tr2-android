package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.billPayment.request.BillPaymentRequest;
import ir.trap.tractor.android.apiServices.model.billPayment.response.BillPaymentResponse;
import ir.trap.tractor.android.apiServices.model.getInfoBill.request.GetInfoBillRequest;
import ir.trap.tractor.android.apiServices.model.getInfoBill.response.GetInfoBillResponse;
import ir.trap.tractor.android.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillRequest;
import ir.trap.tractor.android.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillResponse;
import ir.trap.tractor.android.apiServices.model.getMenu.request.GetMenuRequest;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuResponse;

public class GetMenuService extends BasePart
{

    public GetMenuService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getMenu(OnServiceStatus<WebServiceClass<GetMenuResponse>> listener, GetMenuRequest request)
    {
        start(getServiceGenerator().createService().getMenu(request), listener);
    }
}
