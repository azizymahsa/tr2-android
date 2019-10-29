package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.card.addCard.request.AddCardRequest;
import ir.trap.tractor.android.apiServices.model.shetacChangePass2.request.ShetacChangePass2Request;

public class SheatcChangePassService extends BasePart
{

    public SheatcChangePassService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void sheatcChangePassService(ShetacChangePass2Request request, OnServiceStatus<WebServiceClass<Object>> listener)
    {
        start(getServiceGenerator().createService().doChangePass(request), listener);
    }
}
