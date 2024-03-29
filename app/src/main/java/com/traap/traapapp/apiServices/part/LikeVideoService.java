package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.bookMarkPhoto.BookMarkPhotoRequest;
import com.traap.traapapp.apiServices.model.bookMarkPhoto.BookMarkPhotoResponse;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoRequest;
import com.traap.traapapp.apiServices.model.likeVideo.LikeVideoResponse;
import com.traap.traapapp.apiServices.model.photo.response.Content;

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

    public void bookMarkVideoService(Integer videoId, BookMarkPhotoRequest request, OnServiceStatus<WebServiceClass<BookMarkPhotoResponse>> listener)
    {
        start(getServiceGenerator().createService().bookMarkVideo(videoId), listener);
    }

    public void likePhotoService(Integer videoId, LikeVideoRequest request, OnServiceStatus<WebServiceClass<LikeVideoResponse>> listener)
    {
        start(getServiceGenerator().createService().likePhoto(videoId), listener);
    }

    public void bookMarkPhotoService(Integer photoId, BookMarkPhotoRequest request, OnServiceStatus<WebServiceClass<BookMarkPhotoResponse>> listener)
    {
        start(getServiceGenerator().createService().bookMarkPhoto(photoId), listener);
    }

    public void getPhotoDetailService(Integer photoId, BookMarkPhotoRequest request, OnServiceStatus<WebServiceClass<Content>> listener)
    {
        start(getServiceGenerator().createService().getPhotoDetail(photoId), listener);
    }
}