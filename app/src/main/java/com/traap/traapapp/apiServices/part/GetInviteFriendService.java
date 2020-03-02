package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.inviteFriend.InviteFriendResponse;

public class GetInviteFriendService extends BasePart
{
    public GetInviteFriendService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void GetInviteFriendService(OnServiceStatus<WebServiceClass<InviteFriendResponse>> listener)
    {
        start(getServiceGenerator().createService().getInviteFriend(), listener);
    }
}
