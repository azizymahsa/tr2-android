package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.card.Result;
import ir.traap.tractor.android.apiServices.model.card.editCard.request.EditCardRequest;

public class EditCardService extends BasePart
{

    public EditCardService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void editCardService(Integer cardId, EditCardRequest request, OnServiceStatus<WebServiceClass<Result>> listener)
    {
        start(getServiceGenerator().createService().editCard(cardId, request), listener);
    }
}
