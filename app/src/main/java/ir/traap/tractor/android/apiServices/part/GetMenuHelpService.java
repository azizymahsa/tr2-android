package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getMenuHelp.GetMenuHelpRequest;
import ir.traap.tractor.android.apiServices.model.getMenuHelp.GetMenuHelpResponse;

/**
 * Created by MahtabAzizi on 11/20/2019.
 */
public class GetMenuHelpService extends BasePart
{
    public GetMenuHelpService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getMenuHelpService(OnServiceStatus<WebServiceClass<GetMenuHelpResponse>> listener, GetMenuHelpRequest request)
    {
        start(getServiceGenerator().createService().getMenuHelp(), listener);
    }
}
