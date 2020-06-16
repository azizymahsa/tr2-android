package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.billPayment.BillPaymentRequest;
import com.traap.traapapp.apiServices.model.billPayment.BillPaymentResponse;
import com.traap.traapapp.apiServices.model.billPhone.BillPhoneRequest;
import com.traap.traapapp.apiServices.model.billPhone.BillPhoneResponse;

/**
 * Created by MahtabAzizi on 6/14/2020.
 */
public class BillService extends BasePart
    {

    public BillService(ServiceGenerator serviceGenerator)
        {
            super(serviceGenerator);
        }

        @Override
        protected BasePart getPart()
        {
            return this;
        }

        public void bill(OnServiceStatus<WebServiceClass<BillPhoneResponse>> listener, BillPhoneRequest request)
        {
            start(getServiceGenerator().createService().postBillPhone(request), listener);
        }

        public void billPayment(OnServiceStatus<WebServiceClass<BillPaymentResponse>> listener, BillPaymentRequest request)
        {
            start(getServiceGenerator().createService().postBillPayment(request), listener);
        }
}
