package ir.traap.tractor.android.apiServices.part;


import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getShetabCardInfo.reponse.ShetabCardInfoResponse;
import ir.traap.tractor.android.apiServices.model.getShetabCardInfo.request.ShetabCardInfoRequest;

/**
 * Created by Javad.Abadi on 1/2/2019.
 */
public class GetShetabCardInfoService extends BasePart
{
    public GetShetabCardInfoService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getShetabCardInfo(OnServiceStatus<WebServiceClass<ShetabCardInfoResponse>> listener, ShetabCardInfoRequest req)
    {
        start(getServiceGenerator().createService().getShetabCardInfo(req), listener);
    }

}
