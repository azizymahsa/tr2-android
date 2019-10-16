package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.GlobalResponse;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.login.LoginRequest;
import ir.trap.tractor.android.apiServices.model.login.LoginResponse;

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

    public void login(OnServiceStatus<WebServiceClass<GlobalResponse>> listener, LoginRequest request)
    {
        start(getServiceGenerator().createService().login(request), listener);
    }
}
