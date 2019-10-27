package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesRequest;
import ir.trap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesResponse;

/**
 * Created by MahtabAzizi on 10/27/2019.
 */
public class GetAllBoxesService extends BasePart
{

    public GetAllBoxesService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getAllBoxes(OnServiceStatus<WebServiceClass<GetAllBoxesResponse>> listener, GetAllBoxesRequest request,Integer id)
    {
        start(getServiceGenerator().createService().getAllBoxes(id), listener);
    }


}
