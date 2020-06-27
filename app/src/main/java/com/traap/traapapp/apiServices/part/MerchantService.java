/*
package com.traap.traapapp.apiServices.part;


import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.GlobalResponse;
import com.traap.traapapp.apiServices.model.GlobalResponse2;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.tourism.GetUserPassResponse;
import com.traap.traapapp.apiServices.model.tourism.hotel.hotelPayment.request.GdsHotelPaymentRequest;
import com.traap.traapapp.apiServices.model.tourism.hotel.sendMessage.request.HotelSendMessageRequest;
import com.traap.traapapp.apiServices.model.getDecQrCode.DecryptQrRequest;
import com.traap.traapapp.apiServices.model.getDecQrCode.DecryptQrResponse;

public class MerchantService
        extends BasePart
{
    public MerchantService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void decryptQrService(OnServiceStatus<WebServiceClass<DecryptQrResponse>> listener, DecryptQrRequest req)
    {
        start(getServiceGenerator().createService().decryptQr(req), listener);
    }

}
*/
package com.traap.traapapp.apiServices.part;


import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;

import com.traap.traapapp.apiServices.model.getDecQrCode.DecryptQrRequest;
import com.traap.traapapp.apiServices.model.getDecQrCode.DecryptQrResponse;
import com.traap.traapapp.apiServices.model.paymentPrintPos.PaymentPrintPosRequest;
import com.traap.traapapp.apiServices.model.paymentPrintPos.PaymentPrintPosResponse;


public class MerchantService extends BasePart
{

    public MerchantService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }


    public void GetdecryptQrService(OnServiceStatus<WebServiceClass<DecryptQrResponse>> listener, DecryptQrRequest req)
    {
        start(getServiceGenerator().createService().decryptQr(req), listener);
    }
    public void PaymentPrintPosService(OnServiceStatus<WebServiceClass<PaymentPrintPosResponse>> listener, PaymentPrintPosRequest req)
    {
        start(getServiceGenerator().createService().getPayment(req), listener);
    }
}
