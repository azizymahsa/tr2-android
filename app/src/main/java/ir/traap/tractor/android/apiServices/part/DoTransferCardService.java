package ir.traap.tractor.android.apiServices.part;


import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.doTransferCard.request.DoTransferRequest;
import ir.traap.tractor.android.apiServices.model.doTransferCard.response.DoTransferResponse;

/**
 * Created by Javad.Abadi on 1/2/2019.
 */
public class DoTransferCardService extends BasePart
{
    public DoTransferCardService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getDoTransfer(OnServiceStatus<WebServiceClass<DoTransferResponse>> listener, DoTransferRequest req)
    {
        start(getServiceGenerator().createService().doTransferCard(req), listener);
    }


}
