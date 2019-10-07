package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.billPayment.request.BillPaymentRequest;
import ir.trap.tractor.android.apiServices.model.billPayment.response.BillPaymentResponse;
import ir.trap.tractor.android.apiServices.model.getInfoBill.request.GetInfoBillRequest;
import ir.trap.tractor.android.apiServices.model.getInfoBill.response.GetInfoBillResponse;
import ir.trap.tractor.android.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillRequest;
import ir.trap.tractor.android.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillResponse;

/**
 * Created by Javad.Abadi on 1/31/2019.
 */
public class GetBillInfoService extends BasePart
{
    public GetBillInfoService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    //    public void getBillInfoService(OnServiceStatus<GetBillInfoResponse> listener, GetBillInfoRequest request) {
//        start(getServiceGenerator().createService().getBillInfo(request), listener);
//    }
    public void getInfoPhoneBill(OnServiceStatus<GetInfoPhoneBillResponse> listener, GetInfoPhoneBillRequest request)
    {
        start(getServiceGenerator().createService().getInfoPhoneBill(request), listener);
    }

    public void callBillPayment(OnServiceStatus<BillPaymentResponse> listener, BillPaymentRequest request)
    {
        start(getServiceGenerator().createService().billPayment(request), listener);
    }

    public void callInfoBill(OnServiceStatus<GetInfoBillResponse> listener, GetInfoBillRequest request)
    {
        start(getServiceGenerator().createService().getInfoBill(request), listener);
    }
}