package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.reservationmatch.ReservationRequest;
import ir.trap.tractor.android.apiServices.model.reservationmatch.ReservationResponse;

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
