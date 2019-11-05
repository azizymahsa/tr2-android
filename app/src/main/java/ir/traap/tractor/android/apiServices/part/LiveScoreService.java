package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.model.league.request.GetLeagueRequest;
import ir.traap.tractor.android.ui.fragments.leaguse.LeagueTableFragment;


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
}




