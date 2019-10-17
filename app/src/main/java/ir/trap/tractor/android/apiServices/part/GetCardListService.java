package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.card.getCardList.GetCardListResponse;
import ir.trap.tractor.android.apiServices.model.getMenu.request.GetMenuRequest;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuResponse;

public class GetCardListService extends BasePart
{

    public GetCardListService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getMenu(OnServiceStatus<WebServiceClass<GetCardListResponse>> listener)
    {
        start(getServiceGenerator().createService().getCardList(), listener);
    }
}
