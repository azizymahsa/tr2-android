package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.shetacForgotPass2.request.ShetacForgotPass2Request;

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
