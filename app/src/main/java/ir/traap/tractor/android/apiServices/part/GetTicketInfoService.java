package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeRequest;
import ir.traap.tractor.android.apiServices.model.getBillCodePayCode.GetBillCodePayCodeResponse;
import ir.traap.tractor.android.apiServices.model.getTicketInfo.GetTicketInfoRequest;
import ir.traap.tractor.android.apiServices.model.getTicketInfo.GetTicketInfoResponse;

/**
 * Created by MahtabAzizi on 11/6/2019.
 */
public class GetTicketInfoService extends BasePart
{
    public GetTicketInfoService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getTicketInfoService(OnServiceStatus<WebServiceClass<GetTicketInfoResponse>> listener, GetTicketInfoRequest request)
    {
        start(getServiceGenerator().createService().getTicketInfo(request), listener);
    }
}
