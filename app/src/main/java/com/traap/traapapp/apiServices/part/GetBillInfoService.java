package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.billPayment.request.BillPaymentRequest;
import com.traap.traapapp.apiServices.model.billPayment.response.BillPaymentResponse;
import com.traap.traapapp.apiServices.model.getInfoBill.request.GetInfoBillRequest;
import com.traap.traapapp.apiServices.model.getInfoBill.response.GetInfoBillResponse;
import com.traap.traapapp.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillRequest;
import com.traap.traapapp.apiServices.model.getInfoPhoneBill.GetInfoPhoneBillResponse;

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
    public void getInfoPhoneBill(OnServiceStatus<WebServiceClass<GetInfoPhoneBillResponse>> listener, GetInfoPhoneBillRequest request)
    {
        start(getServiceGenerator().createService().getInfoPhoneBill(request), listener);
    }

    public void callBillPayment(OnServiceStatus<WebServiceClass<BillPaymentResponse>> listener, BillPaymentRequest request)
    {
        start(getServiceGenerator().createService().billPayment(request), listener);
    }

    public void callInfoBill(OnServiceStatus<WebServiceClass<GetInfoBillResponse>> listener, GetInfoBillRequest request)
    {
        start(getServiceGenerator().createService().getInfoBill(request), listener);
    }
}