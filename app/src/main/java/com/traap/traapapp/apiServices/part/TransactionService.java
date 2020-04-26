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
import com.traap.traapapp.apiServices.model.getTransaction.ResponseTransaction;
import com.traap.traapapp.apiServices.model.getTypeTransaction.TypeTransaction;
import com.traap.traapapp.apiServices.model.media.category.TypeCategory;

import java.util.ArrayList;


public class TransactionService extends BasePart
{

    public TransactionService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getTypeTransactionList(OnServiceStatus<WebServiceClass<ArrayList<TypeCategory>>> listener)
    {
        start(getServiceGenerator().createService().getTypeTransactionList(), listener);
    }

    public void getTransactionList(OnServiceStatus<WebServiceClass<ResponseTransaction>> listener)
    {
        start(getServiceGenerator().createService().getTransactionList(), listener);
    }

    public void getTransactionListByFilter(String typeTransactionIds,
                                           Integer priceFrom,
                                           Integer priceTo,
                                           String dateFrom,
                                           String dateTo,
                                           Boolean status,
//                                           String searchText,
                                           OnServiceStatus<WebServiceClass<ResponseTransaction>> listener)
    {
        start(getServiceGenerator().createService().getTransactionListByFilter(
                typeTransactionIds,
                priceFrom,
                priceTo,
                dateFrom,
                dateTo,
                status
//                searchText
                ), listener);
    }

    public void getTransactionListByFilterForAllStatus(String typeTransactionIds,
                                           Integer priceFrom,
                                           Integer priceTo,
                                           String dateFrom,
                                           String dateTo,
//                                           String searchText,
                                           OnServiceStatus<WebServiceClass<ResponseTransaction>> listener)
    {
        start(getServiceGenerator().createService().getTransactionListByFilterForAllStatus(
                typeTransactionIds,
                priceFrom,
                priceTo,
                dateFrom,
                dateTo
//                searchText
                ), listener);
    }

    public void getTransactionListBySearch(String searchText, OnServiceStatus<WebServiceClass<ResponseTransaction>> listener)
    {
        start(getServiceGenerator().createService().getTransactionListBySearch(searchText), listener);
    }

}
