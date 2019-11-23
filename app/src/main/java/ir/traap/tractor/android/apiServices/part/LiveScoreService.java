package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.model.league.getLeagues.request.GetLeagueRequest;
import ir.traap.tractor.android.apiServices.model.league.pastResult.request.RequestPastResult;
import ir.traap.tractor.android.ui.fragments.matchSchedule.leaguse.LeagueTableFragment;
import ir.traap.tractor.android.ui.fragments.matchSchedule.leaguse.pastResult.PastResultFragment;


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




