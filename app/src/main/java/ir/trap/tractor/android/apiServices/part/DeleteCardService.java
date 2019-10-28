package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.card.addCard.request.AddCardRequest;

public class DeleteCardService extends BasePart
{

    public DeleteCardService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void deleteCardService(Integer cardId, OnServiceStatus<WebServiceClass<Object>> listener)
    {
        start(getServiceGenerator().createService().deleteCard(cardId), listener);
    }
}
