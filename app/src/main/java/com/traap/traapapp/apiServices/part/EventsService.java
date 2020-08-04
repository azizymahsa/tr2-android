package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.billPayment.BillPaymentResponse;
import com.traap.traapapp.apiServices.model.event.GetAllEventResponse;
import com.traap.traapapp.apiServices.model.event.getEventByid.GetEventByIdResponse;
import com.traap.traapapp.apiServices.model.event.getWorkshopById.GetWorkShopByIdResponse;
import com.traap.traapapp.apiServices.model.event.participant.ParticipantEventIdResponse;
import com.traap.traapapp.apiServices.model.event.payment.RequestEventPay;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.getEvaluationResult.request.GetPlayerEvaluationResultRequest;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.getEvaluationResult.response.GetPlayerEvaluationRequestResponse;

import java.util.List;


/**
 * Created by MahsaAzizi on 9/29/2020.
 */
public class EventsService extends BasePart
{
    public EventsService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }


    public void getAllEvents(OnServiceStatus<WebServiceClass<GetAllEventResponse>> response)
    {
        start(getServiceGenerator().createService().getAllEvent(), response);
    }

    public void getParticipantRetrieve(Integer id, OnServiceStatus<WebServiceClass<ParticipantEventIdResponse>> response)
    {
        start(getServiceGenerator().createService().getParticipantRetrieve(id), response);
    }

    public void getEventById(Integer id, OnServiceStatus<WebServiceClass<GetEventByIdResponse>> response)
    {
        start(getServiceGenerator().createService().getEventById(id), response);
    }

    public void getWorkshopsById(Integer id, OnServiceStatus<WebServiceClass<GetWorkShopByIdResponse>> response)
    {
        start(getServiceGenerator().createService().getWorkshopsById(id), response);
    }

    public void postWorkshopPay(RequestEventPay request,OnServiceStatus<WebServiceClass<BillPaymentResponse>> listener )
    {
        start(getServiceGenerator().createService().postWorkshopPay(request), listener);
    }


}
