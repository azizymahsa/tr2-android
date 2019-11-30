package com.traap.traapapp.apiServices.part;


import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.doTransferCard.request.DoTransferRequest;
import com.traap.traapapp.apiServices.model.doTransferCard.response.DoTransferResponse;

/**
 * Created by Javad.Abadi on 1/2/2019.
 */
public class DoTransferCardService extends BasePart
{
    public DoTransferCardService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getDoTransfer(OnServiceStatus<WebServiceClass<DoTransferResponse>> listener, DoTransferRequest req)
    {
        start(getServiceGenerator().createService().doTransferCard(req), listener);
    }


}
