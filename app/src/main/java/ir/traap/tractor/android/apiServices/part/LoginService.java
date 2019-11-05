package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.login.LoginRequest;
import ir.traap.tractor.android.apiServices.model.login.LoginResponse;

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
