package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletRequest;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletResponse;
import com.traap.traapapp.apiServices.model.withdrawWallet.WithdrawWalletRequest;
import com.traap.traapapp.apiServices.model.withdrawWallet.WithdrawWalletResponse;

/**
 * Created by MahsaAzizi on 03/01/2020.
 */
public class WithdrawWalletService extends BasePart
{
    public WithdrawWalletService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void WithdrawWalletService(OnServiceStatus<WebServiceClass<WithdrawWalletResponse>> listener, WithdrawWalletRequest req)
    {
        start(getServiceGenerator().createService().withdrawWallet(req), listener);
    }
}

