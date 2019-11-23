package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.mainVideos.MainVideoRequest;
import ir.traap.tractor.android.apiServices.model.mainVideos.MainVideosResponse;

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

    public void getMainVideos(OnServiceStatus<WebServiceClass<MainVideosResponse>> listener, MainVideoRequest request)
    {
        start(getServiceGenerator().createService().getMainVideos(), listener);
    }
}

