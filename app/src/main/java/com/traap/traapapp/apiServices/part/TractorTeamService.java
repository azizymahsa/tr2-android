package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.spectatorInfo.GetSpectatorListResponse;
import com.traap.traapapp.apiServices.model.spectatorInfo.SpectatorInfoResponse;
import com.traap.traapapp.apiServices.model.tractorTeam.TractorTeamResponse;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class TractorTeamService extends BasePart
{

    public TractorTeamService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void traktor( OnServiceStatus<WebServiceClass<TractorTeamResponse>> listener)
    {
        start(getServiceGenerator().createService().traktor(), listener);
    }


}
