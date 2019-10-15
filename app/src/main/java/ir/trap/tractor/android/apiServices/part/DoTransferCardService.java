package ir.trap.tractor.android.apiServices.part;


import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.doTransferCard.request.DoTransferRequest;
import ir.trap.tractor.android.apiServices.model.doTransferCard.response.DoTransferResponse;
import ir.trap.tractor.android.apiServices.model.getShetabCardInfo.reponse.ShetabCardInfoResponse;
import ir.trap.tractor.android.apiServices.model.getShetabCardInfo.request.ShetabCardInfoRequest;

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
