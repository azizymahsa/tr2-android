package ir.trap.tractor.android.apiServices.part;

import java.util.ArrayList;
import java.util.List;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.allService.response.Datum;
import ir.trap.tractor.android.apiServices.model.allService.response.ResponseAllService;
import ir.trap.tractor.android.apiServices.model.getMenu.request.GetMenuRequest;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuItemResponse;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuResponse;

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
    public void getMenuAll(OnServiceStatus<WebServiceClass<ArrayList<GetMenuItemResponse>>> listener, GetMenuRequest request)
    {
        start(getServiceGenerator().createService().getMenuAll(request), listener);
    }
}
