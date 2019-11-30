package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getMenuHelp.GetMenuHelpRequest;
import com.traap.traapapp.apiServices.model.getMenuHelp.GetMenuHelpResponse;

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
