package com.traap.traapapp.apiServices.part;

import androidx.annotation.Nullable;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.profile.putProfile.request.SendProfileRequest;
import com.traap.traapapp.apiServices.model.profile.putProfile.response.SendProfileResponse;

import java.util.Map;

import okhttp3.MultipartBody;

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

    public void sendProfileService(Map<String, Object> fields,
                                   @Nullable MultipartBody.Part ImageFile,
                                   OnServiceStatus<WebServiceClass<SendProfileResponse>> listener)
    {
        start(getServiceGenerator().createService().sendProfile(fields, ImageFile), listener);
    }
}
