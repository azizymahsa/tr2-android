package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyChargeCard.BuyChargeCardRequest;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletRequest;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletResponse;
import com.traap.traapapp.apiServices.model.buyPackage.response.BuyPackageWalletResponse;
import com.traap.traapapp.apiServices.model.buyPackageCard.request.BuyPackageCardRequest;
import com.traap.traapapp.apiServices.model.paymentMatchCard.PaymentMatchCardRequest;
import com.traap.traapapp.apiServices.model.paymentWallet.ResponsePaymentWallet;

/**
 * Created by Javad.Abadi on 12/10/2019.
 */
public class BuyCardService extends BasePart
{
    public BuyCardService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void buyChargeCardService(BuyChargeCardRequest req, OnServiceStatus<WebServiceClass<BuyChargeWalletResponse>> listener)
    {
        start(getServiceGenerator().createService().buyChargeCard(req), listener);
    }

    public void buyPackageCardService(BuyPackageCardRequest req, OnServiceStatus<WebServiceClass<BuyPackageWalletResponse>> listener)
    {
        start(getServiceGenerator().createService().buyPackageCard(req), listener);
    }

    public void buyMatchTicketByCardService(PaymentMatchCardRequest req, OnServiceStatus<WebServiceClass<ResponsePaymentWallet>> listener)
    {
        start(getServiceGenerator().createService().buyMatchTicketByCard(req), listener);
    }

}

