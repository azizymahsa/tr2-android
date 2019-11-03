package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.predict.getPredict.response.GetPredictResponse;

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
}
