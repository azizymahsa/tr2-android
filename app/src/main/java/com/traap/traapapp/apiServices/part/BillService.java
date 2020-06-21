package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.billCode.BillCodeResponse;
import com.traap.traapapp.apiServices.model.billElectricity.BillElectricityRequest;
import com.traap.traapapp.apiServices.model.billElectricity.BillElectricityResponse;
import com.traap.traapapp.apiServices.model.billPayment.BillPaymentRequest;
import com.traap.traapapp.apiServices.model.billPayment.BillPaymentResponse;
import com.traap.traapapp.apiServices.model.billPhone.BillPhoneRequest;
import com.traap.traapapp.apiServices.model.billPhone.BillPhoneResponse;
import com.traap.traapapp.apiServices.model.payBillCar.RequestPayBillCar;

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

        public void billElectricity(OnServiceStatus<WebServiceClass<BillElectricityResponse>> listener, BillElectricityRequest request)
        {
            start(getServiceGenerator().createService().postBillElectricity(request), listener);
        }
        public void billGaz(OnServiceStatus<WebServiceClass<BillCodeResponse>> listener, BillPhoneRequest request)
        {
            start(getServiceGenerator().createService().postBillGaz(request), listener);
        }
        public void billMci(OnServiceStatus<WebServiceClass<BillPhoneResponse>> listener, BillPhoneRequest request)
        {
            start(getServiceGenerator().createService().postBillMci(request), listener);
        }
        public void billWater(OnServiceStatus<WebServiceClass<BillCodeResponse>> listener, BillPhoneRequest request)
        {
            start(getServiceGenerator().createService().postBillWater(request), listener);
        }

        public void billPayment(OnServiceStatus<WebServiceClass<BillPaymentResponse>> listener, BillPaymentRequest request)
        {
            start(getServiceGenerator().createService().postBillPayment(request), listener);
        }

        public void postBillCarPayment(OnServiceStatus<WebServiceClass<BillPaymentResponse>> listener, RequestPayBillCar request)
        {
            start(getServiceGenerator().createService().postBillCarPayment(request), listener);
        }

    }
