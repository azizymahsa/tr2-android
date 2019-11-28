package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.categoryByIdVideo.CategoryByIdVideosRequest;
import ir.traap.tractor.android.apiServices.model.categoryByIdVideo.CategoryByIdVideosResponse;
import ir.traap.tractor.android.apiServices.model.photo.response.PhotosByIdResponse;

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

    public void categoryByIdVideosService(Integer categoryId, CategoryByIdVideosRequest request, OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>> listener)
    {
        start(getServiceGenerator().createService().getCategoryByIdVideos(categoryId), listener);
    }

    public void categoryByIdPhotosService(Integer categoryId, CategoryByIdVideosRequest request, OnServiceStatus<WebServiceClass<CategoryByIdVideosResponse>> listener)
    {
        start(getServiceGenerator().createService().getCategoryByIdPhotos(categoryId), listener);
    }
    public void photosByIdPhotosService(Integer categoryId, CategoryByIdVideosRequest request, OnServiceStatus<WebServiceClass<PhotosByIdResponse>> listener)
    {
        start(getServiceGenerator().createService().getPhotosById(categoryId), listener);
    }
}