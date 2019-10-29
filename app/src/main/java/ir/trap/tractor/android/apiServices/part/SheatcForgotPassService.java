package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.shetacChangePass2.request.ShetacChangePass2Request;
import ir.trap.tractor.android.apiServices.model.shetacForgotPass2.request.ShetacForgotPass2Request;

public class SheatcForgotPassService extends BasePart
{

    public SheatcForgotPassService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void sheatcForgotPassService(ShetacForgotPass2Request request, OnServiceStatus<WebServiceClass<Object>> listener)
    {
        start(getServiceGenerator().createService().doForgotPass(request), listener);
    }
}
