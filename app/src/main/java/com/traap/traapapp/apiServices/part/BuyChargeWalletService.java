package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletRequest;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletResponse;

/**
 * Created by MahtabAzizi on 12/10/2019.
 */
public class BuyChargeWalletService extends BasePart
{
    public BuyChargeWalletService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void BuyChargeWalletService(OnServiceStatus<WebServiceClass<BuyChargeWalletResponse>> listener, BuyChargeWalletRequest req)
    {
        start(getServiceGenerator().createService().buyChargeWallet(req), listener);
    }
}

