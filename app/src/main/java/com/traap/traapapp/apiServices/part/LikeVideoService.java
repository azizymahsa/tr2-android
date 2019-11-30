package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoRequest;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoResponse;

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
    public void likePhotoService(Integer videoId, LikeVideoRequest request, OnServiceStatus<WebServiceClass<LikeVideoResponse>> listener)
    {
        start(getServiceGenerator().createService().likePhoto(videoId), listener);
    }
}