package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.profile.getProfile.response.GetProfileResponse;
import ir.traap.tractor.android.apiServices.model.profile.putProfile.request.SendProfileRequest;
import ir.traap.tractor.android.apiServices.model.profile.putProfile.response.SendProfileResponse;

/**
 * Created by Javad.Abadi on 10/21/2019.
 */
public class SendProfileService extends BasePart
{
    public SendProfileService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void sendProfileService(SendProfileRequest request, OnServiceStatus<WebServiceClass<SendProfileResponse>> listener)
    {
        start(getServiceGenerator().createService().sendProfile(request), listener);
    }
}
