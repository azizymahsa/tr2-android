package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getTransaction.TransactionDetailResponse;

/**
 * Created by MahtabAzizi on 12/11/2019.
 */
public class TransactionDetailService extends BasePart
{

    public TransactionDetailService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }


    public void getTransactionDetail(Integer transactionId,OnServiceStatus<WebServiceClass<TransactionDetailResponse>> listener )
    {
        start(getServiceGenerator().createService().getTransactionDetail(transactionId), listener);
    }

}
