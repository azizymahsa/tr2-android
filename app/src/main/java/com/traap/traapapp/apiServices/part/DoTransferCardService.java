package com.traap.traapapp.apiServices.part;


import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.doTransferCard.request.DoTransferRequest;
import com.traap.traapapp.apiServices.model.doTransferCard.response.DoTransferResponse;
import com.traap.traapapp.apiServices.model.getReport.request.GetReportRequest;
import com.traap.traapapp.apiServices.model.getReport.response.GetReportResponse;
import com.traap.traapapp.apiServices.model.techs.GetTechsIdResponse;
import com.traap.traapapp.apiServices.model.techs.RequestSetFavoritePlayer;

/**
 * Created by Javad.Abadi on 1/2/2019.
 */
public class DoTransferCardService extends BasePart
{
    public DoTransferCardService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getDoTransfer(OnServiceStatus<WebServiceClass<DoTransferResponse>> listener, DoTransferRequest req)
    {
        start(getServiceGenerator().createService().doTransferCard(req), listener);
    }


    public void getReport(OnServiceStatus<WebServiceClass<GetReportResponse>> listener, GetReportRequest req)
    {
        start(getServiceGenerator().createService().getReport(req), listener);
    }

    public void potFavoritPlayer(OnServiceStatus<WebServiceClass<GetTechsIdResponse>> listener, RequestSetFavoritePlayer req)
    {
        start(getServiceGenerator().createService().potFavoritPlayer(req), listener);
    }


}
