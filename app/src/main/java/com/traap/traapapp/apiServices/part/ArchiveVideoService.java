package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.archiveVideo.ArchiveVideoRequest;
import com.traap.traapapp.apiServices.model.archiveVideo.ArchiveVideoResponse;
import com.traap.traapapp.apiServices.model.news.archive.NewsArchiveListByIdResponse;

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

    public void getArchiveVideo(String categoryId, OnServiceStatus<WebServiceClass<ArchiveVideoResponse>> listener)
    {
        start(getServiceGenerator().createService().getVideosArchive(categoryId), listener);
    }

    public void getVideosArchiveCategoryByIds(String categoryIds,
                                            String dateFrom,
                                            String dateTo,
                                            String searchText,
                                            OnServiceStatus<WebServiceClass<ArchiveVideoResponse>> listener)
    {
        start(getServiceGenerator().createService().getVideosArchiveCategoryByIds(
                categoryIds,
                dateFrom,
                dateTo,
                searchText
        ), listener);
    }

    public void getBookMarkVideo(OnServiceStatus<WebServiceClass<ArchiveVideoResponse>> listener)
    {
        start(getServiceGenerator().createService().getListBookmarkVideos(), listener);
    }

}

