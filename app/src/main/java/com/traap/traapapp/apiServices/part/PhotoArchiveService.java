package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.archiveVideo.ArchiveVideoRequest;
import com.traap.traapapp.apiServices.model.archiveVideo.ArchiveVideoResponse;
import com.traap.traapapp.apiServices.model.media.category.MediaArchiveCategoryResponse;
import com.traap.traapapp.apiServices.model.news.archive.NewsArchiveListByIdResponse;
import com.traap.traapapp.apiServices.model.photo.archive.PhotoArchiveResponse;

/**
 * Created by Javad.Abadi on 11/27/2019.
 */
public class PhotoArchiveService extends BasePart
{
    public PhotoArchiveService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getArchivePhoto( String category_id, OnServiceStatus<WebServiceClass<PhotoArchiveResponse>> listener)
    {
        start(getServiceGenerator().createService().getArchivePhotos(category_id), listener);
    }

    public void getArchivePhotoByIds(String categoryIds,
                                            String dateFrom,
                                            String dateTo,
                                            String searchText,
                                            OnServiceStatus<WebServiceClass<PhotoArchiveResponse>> listener)
    {
        start(getServiceGenerator().createService().getArchivePhotosByIds(
                categoryIds,
                dateFrom,
                dateTo,
                searchText
        ), listener);
    }

    public void getPhotosArchiveCategory(OnServiceStatus<WebServiceClass<MediaArchiveCategoryResponse>> listener)
    {
        start(getServiceGenerator().createService().getPhotosArchiveCategory(), listener);
    }

    public void getBookMarkPhoto(OnServiceStatus<WebServiceClass<PhotoArchiveResponse>> listener)
    {
        start(getServiceGenerator().createService().getListBookmarkPhotos(), listener);
    }
}

