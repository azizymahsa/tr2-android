package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeRequest;
import ir.traap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeResponse;
import ir.traap.tractor.android.apiServices.model.profile.getProfile.response.GetProfileResponse;

/**
 * Created by Javad.Abadi on 10/21/2019.
 */
public class GetProfileService extends BasePart
{
    public GetProfileService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getProfileService(OnServiceStatus<WebServiceClass<GetProfileResponse>> listener)
    {
        start(getServiceGenerator().createService().getProfile(), listener);
    }
}
