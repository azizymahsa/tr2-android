package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getAllMenuServices.response.GetAllMenuResponse;
import ir.traap.tractor.android.apiServices.model.getHistory.ResponseHistory;
import ir.traap.tractor.android.apiServices.model.getMenu.request.GetMenuRequest;
import ir.traap.tractor.android.apiServices.model.getMenu.response.GetMenuResponse;

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
