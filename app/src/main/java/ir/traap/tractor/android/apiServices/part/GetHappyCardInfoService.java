package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getHappyCardInfo.GetHappyCardInfoRequest;
import ir.traap.tractor.android.apiServices.model.getHappyCardInfo.response.GetHappyCardInfoResponse;

/**
 * Created by Javad.Abadi on 8/19/2018.
 */
public class GetHappyCardInfoService extends BasePart
{
    public GetHappyCardInfoService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void GetCardInfoService(OnServiceStatus<WebServiceClass<GetHappyCardInfoResponse>> listener, GetHappyCardInfoRequest req)
    {
        start(getServiceGenerator().createService().getHappyCardInfo(req), listener);
    }
}