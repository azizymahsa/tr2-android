package com.traap.traapapp.apiServices.part;

import androidx.annotation.Nullable;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.editUser.sendCodeReq.SendCodeReq;
import com.traap.traapapp.apiServices.model.editUser.sendCodeRes.SendCodeRes;
import com.traap.traapapp.apiServices.model.editUser.verifyReq.VerifyRequest;
import com.traap.traapapp.apiServices.model.editUser.verifyRes.VerifyResponse;
import com.traap.traapapp.apiServices.model.profile.deleteProfile.DeleteProfileResponse;
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

    public void sendProfileService(SendProfileRequest request,
                                   OnServiceStatus<WebServiceClass<SendProfileResponse>> listener)
    {
        start(getServiceGenerator().createService().sendProfile(request), listener);
    }

    public void sendProfilePhoto(MultipartBody.Part ImageFile,
                                   OnServiceStatus<WebServiceClass<Object>> listener)
    {
        start(getServiceGenerator().createService().sendProfilePhoto(ImageFile), listener);
    }

    public void deleteProfilePhoto(OnServiceStatus<WebServiceClass<DeleteProfileResponse>> listener)
    {
        start(getServiceGenerator().createService().deleteProfilePhoto(), listener);
    }
    public void sendCodeEditUser(SendCodeReq SendCodeReq,OnServiceStatus<WebServiceClass<SendCodeRes>> listener)
    {
        start(getServiceGenerator().createService().sendCodeEditUser(SendCodeReq), listener);
    }
    public void editUserVerify(VerifyRequest SendCodeReq,OnServiceStatus<WebServiceClass<VerifyResponse>> listener)
    {
        start(getServiceGenerator().createService().editUserVerify(SendCodeReq), listener);
    }
}
