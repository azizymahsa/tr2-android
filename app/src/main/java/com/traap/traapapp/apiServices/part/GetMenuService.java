package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getAllMenuServices.response.GetAllMenuResponse;
import com.traap.traapapp.apiServices.model.getHistory.ResponseHistory;
import com.traap.traapapp.apiServices.model.getMenu.request.GetMenuRequest;
import com.traap.traapapp.apiServices.model.getMenu.response.GetMenuResponse;

public class GetMenuService extends BasePart
{

    public GetMenuService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getMenu(OnServiceStatus<WebServiceClass<GetMenuResponse>> listener, GetMenuRequest request)
    {
        start(getServiceGenerator().createService().getMenu(request), listener);
    }
    public void getMenuAll(OnServiceStatus<WebServiceClass<GetAllMenuResponse>> listener, GetMenuRequest request)
    {
        start(getServiceGenerator().createService().getMenuAll(request), listener);
    }

    public void getHistory(OnServiceStatus<WebServiceClass<ResponseHistory>> listener)
    {
        start(getServiceGenerator().createService().getHistory(), listener);
    }
}
