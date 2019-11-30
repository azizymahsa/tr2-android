package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getAllBoxes.GetAllBoxesRequest;
import com.traap.traapapp.apiServices.model.getAllBoxes.GetAllBoxesResponse;

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
