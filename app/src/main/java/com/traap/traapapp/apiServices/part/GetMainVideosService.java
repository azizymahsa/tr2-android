package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.mainVideos.MainVideoRequest;
import com.traap.traapapp.apiServices.model.mainVideos.MainVideosResponse;

/**
 * Created by MahtabAzizi on 11/23/2019.
 */
public class GetMainVideosService extends BasePart
{
    public GetMainVideosService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getMainVideos(OnServiceStatus<WebServiceClass<MainVideosResponse>> listener)
    {
        start(getServiceGenerator().createService().getMainVideos(), listener);
    }
}

