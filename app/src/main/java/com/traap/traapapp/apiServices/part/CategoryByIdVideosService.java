package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import com.traap.traapapp.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import com.traap.traapapp.apiServices.model.media.category.MediaArchiveCategoryResponse;
import com.traap.traapapp.apiServices.model.photo.response.PhotosByIdResponse;

/**
 * Created by MahtabAzizi on 11/25/2019.
 */
public class CategoryByIdVideosService extends BasePart
{

    public CategoryByIdVideosService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void categoryByIdVideosService(Integer categoryId, OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>> listener)
    {
        start(getServiceGenerator().createService().getCategoryByIdVideos(categoryId), listener);
    }

    public void getVideosArchiveCategoryService(OnServiceStatus<WebServiceClass<MediaArchiveCategoryResponse>> listener)
    {
        start(getServiceGenerator().createService().getVideosArchiveCategory(), listener);
    }

    public void categoryByIdVideosService2(Integer categoryId, CategoryByIdVideosRequest request, OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>> listener)
    {
        start(getServiceGenerator().createService().getCategoryByIdVideos2(categoryId), listener);
    }

    public void categoryByIdPhotosService(Integer categoryId, OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>> listener)
    {
        start(getServiceGenerator().createService().getCategoryByIdPhotos(categoryId), listener);
    }
    public void categoryByIdPhotosService2(Integer categoryId, CategoryByIdVideosRequest request, OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>> listener)
    {
        start(getServiceGenerator().createService().getCategoryByIdPhotos2(categoryId), listener);
    }
    public void photosByIdPhotosService(Integer categoryId, CategoryByIdVideosRequest request, OnServiceStatus<WebServiceClass<PhotosByIdResponse>> listener)
    {
        start(getServiceGenerator().createService().getPhotosById(categoryId), listener);
    }
}