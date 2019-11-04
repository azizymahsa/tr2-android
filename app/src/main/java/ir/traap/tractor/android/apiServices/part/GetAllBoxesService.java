package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesRequest;
import ir.traap.tractor.android.apiServices.model.getAllBoxes.GetAllBoxesResponse;

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

    public void getAllBoxes(OnServiceStatus<WebServiceClass<GetAllBoxesResponse>> listener, GetAllBoxesRequest request)
    {
        start(getServiceGenerator().createService().getAllBoxes(request), listener);
    }


}
