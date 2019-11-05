package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.mobileCharge.request.MobileChargeRequest;
import ir.traap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;

/**
 * Created by Javad.Abadi on 7/25/2018.
 */
public class MobileChargeService extends BasePart
{
    public MobileChargeService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void MobileChargeService(OnServiceStatus<WebServiceClass<MobileChargeResponse>> listener, MobileChargeRequest req)
    {
        start(getServiceGenerator().createService().getMobileCharge(req), listener);
    }
}




