package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.doTransfer.DoTransferWalletRequest;
import com.traap.traapapp.apiServices.model.doTransfer.DoTransferWalletResponse;

/**
 * Created by MahtabAzizi on 1/14/2020.
 */
public class DoTransferWalletService extends BasePart
{

    public DoTransferWalletService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }


    public void DoTransferWalletService(OnServiceStatus<WebServiceClass<DoTransferWalletResponse>> listener, DoTransferWalletRequest req)
    {
        start(getServiceGenerator().createService().doTransferWallet(req), listener);
    }

}
