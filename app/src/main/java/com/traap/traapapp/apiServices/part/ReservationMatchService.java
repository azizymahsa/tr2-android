package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.reservationmatch.ReservationRequest;
import com.traap.traapapp.apiServices.model.reservationmatch.ReservationResponse;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public class ReservationMatchService extends BasePart
{

    public ReservationMatchService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void reservationMatchService(OnServiceStatus<WebServiceClass<ReservationResponse>> listener, ReservationRequest request)
    {
        start(getServiceGenerator().createService().reservTicket(request), listener);
    }


}
