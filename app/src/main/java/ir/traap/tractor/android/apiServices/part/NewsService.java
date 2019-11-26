/*
package ir.traap.tractor.android.apiServices.part;


import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.GlobalResponse;
import ir.traap.tractor.android.apiServices.model.GlobalResponse2;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.tourism.GetUserPassResponse;
import ir.traap.tractor.android.apiServices.model.tourism.hotel.hotelPayment.request.GdsHotelPaymentRequest;
import ir.traap.tractor.android.apiServices.model.tourism.hotel.sendMessage.request.HotelSendMessageRequest;
import ir.traap.tractor.android.apiServices.model.getDecQrCode.DecryptQrRequest;
import ir.traap.tractor.android.apiServices.model.getDecQrCode.DecryptQrResponse;

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
package ir.traap.tractor.android.apiServices.part;


import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.matchList.MachListResponse;
import ir.traap.tractor.android.apiServices.model.news.archive.response.NewsArchiveListByIdResponse;
import ir.traap.tractor.android.apiServices.model.news.category.response.NewsArchiveCategoryResponse;


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

    public void getNewsArchiveCategoryById(String categoryId, OnServiceStatus<WebServiceClass<NewsArchiveListByIdResponse>> listener)
    {
        start(getServiceGenerator().createService().getNewsArchiveCategoryById(categoryId), listener);
    }

}
