package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getLast5PastMatch.request.Last5PastMatchRequest;
import com.traap.traapapp.apiServices.model.getLast5PastMatch.response.Last5PastMatchResponse;
import com.traap.traapapp.apiServices.model.league.getLeagues.request.GetLeagueRequest;
import com.traap.traapapp.apiServices.model.league.pastResult.request.RequestPastResult;
import com.traap.traapapp.ui.fragments.leagueTable.LeagueTableFragment;
import com.traap.traapapp.ui.fragments.matchSchedule.pastResult.PastResultFragment;


public class LiveScoreService extends BasePart
{
    public LiveScoreService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void LeaguesService(LeagueTableFragment listener, GetLeagueRequest req)
    {
        start(getServiceGenerator().createService().getLeage(req), listener);
    }

    public void PastResultService(PastResultFragment listener, RequestPastResult req)
    {
        start(getServiceGenerator().createService().getPastResult(req), listener);
    }

    public void getPastResult_v2_Service(Last5PastMatchRequest request, OnServiceStatus<WebServiceClass<Last5PastMatchResponse>> response)
    {
        start(getServiceGenerator().createService().getPastResult_v2(request), response);
    }

}




