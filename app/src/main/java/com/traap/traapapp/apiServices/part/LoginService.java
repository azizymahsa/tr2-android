package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.login.LoginRequest;
import com.traap.traapapp.apiServices.model.login.LoginResponse;

/**
 * Created by MahtabAzizi on 10/15/2019.
 */
public class LoginService extends BasePart {
    public LoginService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void login(OnServiceStatus<WebServiceClass<LoginResponse>> listener, LoginRequest request)
    {
        start(getServiceGenerator().createService().login(request), listener);
    }
}
