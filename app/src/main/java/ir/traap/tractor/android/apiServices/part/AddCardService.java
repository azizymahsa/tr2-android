package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.card.Result;
import ir.traap.tractor.android.apiServices.model.card.addCard.request.AddCardRequest;

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

    public void addCardService(AddCardRequest request, OnServiceStatus<WebServiceClass<Result>> listener)
    {
        start(getServiceGenerator().createService().addCard(request), listener);
    }
}
