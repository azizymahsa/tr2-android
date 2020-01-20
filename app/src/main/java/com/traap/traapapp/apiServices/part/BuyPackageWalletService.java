package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyPackage.request.BuyPackageWalletRequest;
import com.traap.traapapp.apiServices.model.buyPackage.response.BuyPackageWalletResponse;

/**
 * Created by MahtabAzizi on 1/19/2020.
 */
public class BuyPackageWalletService extends BasePart
{
    public  BuyPackageWalletService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void  BuyPackageWalletService(OnServiceStatus<WebServiceClass<BuyPackageWalletResponse>> listener, BuyPackageWalletRequest req)
    {
        start(getServiceGenerator().createService().buyPackageWallet(req), listener);
    }
}

