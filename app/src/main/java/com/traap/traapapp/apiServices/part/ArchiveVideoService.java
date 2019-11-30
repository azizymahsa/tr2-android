package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.archiveVideo.ArchiveVideoRequest;
import com.traap.traapapp.apiServices.model.archiveVideo.ArchiveVideoResponse;

/**
 * Created by MahtabAzizi on 11/27/2019.
 */
public class ArchiveVideoService extends BasePart
{
    public ArchiveVideoService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getArchiveVideo(OnServiceStatus<WebServiceClass<ArchiveVideoResponse>> listener, ArchiveVideoRequest request,int categoryId)
    {
        start(getServiceGenerator().createService().getArchiveVideos(categoryId), listener);
    }
    public void getArchivePhoto(OnServiceStatus<WebServiceClass<ArchiveVideoResponse>> listener, ArchiveVideoRequest request)
    {
        start(getServiceGenerator().createService().getArchivePhotos(), listener);
    }
    public void getBookMarkPhoto(OnServiceStatus<WebServiceClass<ArchiveVideoResponse>> listener, ArchiveVideoRequest request)
    {
        start(getServiceGenerator().createService().getListBookmarkPhotos(), listener);
    }
}

