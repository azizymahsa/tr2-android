/*
package com.traap.traapapp.apiServices.part;


import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.GlobalResponse;
import com.traap.traapapp.apiServices.model.GlobalResponse2;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.tourism.GetUserPassResponse;
import com.traap.traapapp.apiServices.model.tourism.hotel.hotelPayment.request.GdsHotelPaymentRequest;
import com.traap.traapapp.apiServices.model.tourism.hotel.sendMessage.request.HotelSendMessageRequest;
import com.traap.traapapp.apiServices.model.getDecQrCode.DecryptQrRequest;
import com.traap.traapapp.apiServices.model.getDecQrCode.DecryptQrResponse;

public class MerchantService
        extends BasePart
{
    public MerchantService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void decryptQrService(OnServiceStatus<WebServiceClass<DecryptQrResponse>> listener, DecryptQrRequest req)
    {
        start(getServiceGenerator().createService().decryptQr(req), listener);
    }

}
*/
package com.traap.traapapp.apiServices.part;


import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.news.archive.response.NewsArchiveListByIdResponse;
import com.traap.traapapp.apiServices.model.news.category.response.NewsArchiveCategoryResponse;
import com.traap.traapapp.apiServices.model.news.details.getComment.response.GetNewsCommentResponse;
import com.traap.traapapp.apiServices.model.news.details.getContent.response.GetNewsDetailsResponse;
import com.traap.traapapp.apiServices.model.news.details.putBookmark.response.NewsBookmarkResponse;
import com.traap.traapapp.apiServices.model.news.details.sendComment.request.SendCommentNewsRequest;
import com.traap.traapapp.apiServices.model.news.details.sendLike.request.LikeNewsDetailRequest;
import com.traap.traapapp.apiServices.model.news.details.sendLike.response.LikeNewsDetailResponse;
import com.traap.traapapp.apiServices.model.news.main.NewsMainResponse;
import java.util.ArrayList;


public class NewsService extends BasePart
{

    public NewsService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }


    public void getNewsArchiveCategory(OnServiceStatus<WebServiceClass<NewsArchiveCategoryResponse>> listener)
    {
        start(getServiceGenerator().createService().getNewsArchiveCategory(), listener);
    }

    public void getNewsArchiveCategoryByIds(String categoryIds, OnServiceStatus<WebServiceClass<NewsArchiveListByIdResponse>> listener)
    {
        start(getServiceGenerator().createService().getNewsArchiveCategoryByIds(categoryIds), listener);
    }

    public void getNewsArchiveCategoryByIdsAndRangeDate(String categoryIds, String createDateRanges, OnServiceStatus<WebServiceClass<NewsArchiveListByIdResponse>> listener)
    {
        start(getServiceGenerator().createService().getNewsArchiveCategoryByIdsAndRangeDate(categoryIds, createDateRanges), listener);
    }

    public void getNewsMain(OnServiceStatus<WebServiceClass<NewsMainResponse>> listener)
    {
        start(getServiceGenerator().createService().getNewsMain(), listener);
    }

    public void getNewsDetails(Integer id, OnServiceStatus<WebServiceClass<GetNewsDetailsResponse>> listener)
    {
        start(getServiceGenerator().createService().getNewsDetails(id), listener);
    }

    public void likeNews(Integer id, LikeNewsDetailRequest request ,OnServiceStatus<WebServiceClass<LikeNewsDetailResponse>> listener)
    {
        start(getServiceGenerator().createService().likeNews(id, request), listener);
    }

    public void sendNewsComment(Integer id, SendCommentNewsRequest request, OnServiceStatus<WebServiceClass<Object>> listener)
    {
        start(getServiceGenerator().createService().sendNewsComment(id, request), listener);
    }

    public void setBookmarkNews(Integer id, OnServiceStatus<WebServiceClass<NewsBookmarkResponse>> listener)
    {
        start(getServiceGenerator().createService().bookmarkNews(id), listener);
    }

    public void getNewsComment(Integer id, OnServiceStatus<WebServiceClass<ArrayList<GetNewsCommentResponse>>> listener)
    {
        start(getServiceGenerator().createService().getNewsComment(id), listener);
    }

    public void setLikeDislikeComment(Integer id, LikeNewsDetailRequest request, OnServiceStatus<WebServiceClass<LikeNewsDetailResponse>> listener)
    {
        start(getServiceGenerator().createService().likeNewsComment(id, request), listener);
    }

    public void getNewsBookmarks(OnServiceStatus<WebServiceClass<NewsArchiveListByIdResponse>> listener)
    {
        start(getServiceGenerator().createService().getNewsBookmarks(), listener);
    }

}
