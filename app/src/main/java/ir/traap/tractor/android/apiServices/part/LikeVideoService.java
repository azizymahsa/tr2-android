package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.likeVideo.LikeVideoRequest;
import ir.traap.tractor.android.apiServices.model.likeVideo.LikeVideoResponse;

/**
 * Created by MahtabAzizi on 11/26/2019.
 */
public class LikeVideoService extends BasePart
{

    public LikeVideoService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void likeVideoService(Integer videoId, LikeVideoRequest request, OnServiceStatus<WebServiceClass<LikeVideoResponse>> listener)
    {
        start(getServiceGenerator().createService().likeVideo(videoId), listener);
    }
}