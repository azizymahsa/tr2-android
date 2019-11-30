package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.profile.getProfile.response.GetProfileResponse;

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
