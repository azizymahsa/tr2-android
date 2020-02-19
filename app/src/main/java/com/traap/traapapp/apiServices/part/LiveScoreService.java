package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
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
}




