package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;

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
