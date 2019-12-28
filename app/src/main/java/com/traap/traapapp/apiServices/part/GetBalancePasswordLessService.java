package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessResponse;
import com.traap.traapapp.apiServices.model.getInfoWallet.GetInfoWalletResponse;
import com.traap.traapapp.apiServices.model.increaseWallet.RequestIncreaseWallet;
import com.traap.traapapp.apiServices.model.increaseWallet.ResponseIncreaseWallet;

/**
 * Created by MahtabAzizi on 12/8/2019.
 */
public class GetBalancePasswordLessService extends BasePart
{

    public GetBalancePasswordLessService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }


    public void GetBalancePasswordLessService(OnServiceStatus<WebServiceClass<GetBalancePasswordLessResponse>> listener, GetBalancePasswordLessRequest req)
    {
        start(getServiceGenerator().createService().getBalancePasswordLess(req), listener);
    }

    public void GetInfoWalletService(OnServiceStatus<WebServiceClass<GetInfoWalletResponse>> listener, GetBalancePasswordLessRequest req)
    {
        start(getServiceGenerator().createService().getInfoWallet(req), listener);
    }
    public void IncreaseInventoryWalletService(OnServiceStatus<WebServiceClass<ResponseIncreaseWallet>> listener, RequestIncreaseWallet req)
    {
        start(getServiceGenerator().createService().getIncInvWallet(req), listener);
    }
}
