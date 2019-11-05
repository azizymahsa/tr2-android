package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.verify.VerifyRequest;
import ir.traap.tractor.android.apiServices.model.verify.VerifyResponse;

/**
 * Created by MahtabAzizi on 10/16/2019.
 */
public class VerifyService extends BasePart {
    public VerifyService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void verify(OnServiceStatus<WebServiceClass<VerifyResponse>> listener, VerifyRequest request)
    {
        start(getServiceGenerator().createService().verify(request), listener);
    }
}
