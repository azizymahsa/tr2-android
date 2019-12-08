package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.ForgetPasswordWalletResponse;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;

/**
 * Created by MahtabAzizi on 12/8/2019.
 */
public class ForgetPasswordWalletService  extends BasePart
{

    public ForgetPasswordWalletService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }


    public void ForgetPasswordWalletService(OnServiceStatus<WebServiceClass<ForgetPasswordWalletResponse>> listener, GetBalancePasswordLessRequest req)
    {
        start(getServiceGenerator().createService().forgetPasswordWallet(req), listener);
    }

}
