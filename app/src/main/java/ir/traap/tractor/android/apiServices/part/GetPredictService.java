package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.predict.getPredict.response.GetPredictResponse;
import ir.traap.tractor.android.apiServices.model.stadium_rules.ResponseStadiumRules;

public class GetPredictService extends BasePart
{

    public GetPredictService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getPredictService(Integer matchId, OnServiceStatus<WebServiceClass<GetPredictResponse>> listener)
    {
        start(getServiceGenerator().createService().getPredict(matchId), listener);
    }

    public void getRulsStadium(Integer id , OnServiceStatus<WebServiceClass<ResponseStadiumRules>> listener)
    {
        start(getServiceGenerator().createService().getRulsStadium(id ), listener);
    }
}
