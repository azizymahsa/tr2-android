package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.card.addCard.request.AddCardRequest;
import ir.trap.tractor.android.apiServices.model.card.getCardList.GetCardListResponse;
import okhttp3.ResponseBody;

public class AddCardService extends BasePart
{

    public AddCardService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void addCardService(AddCardRequest request, OnServiceStatus<WebServiceClass<Object>> listener)
    {
        start(getServiceGenerator().createService().addCard(request), listener);
    }
}
