package ir.trap.tractor.android.apiServices.part;


import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.GlobalResponse2;
import ir.trap.tractor.android.apiServices.model.tourism.GetUserPassResponse;
import ir.trap.tractor.android.apiServices.model.tourism.flight.payment.request.FlightPaymentRequest;

/**
 * Created by Javad.Abadi on 2/23/2019.
 */
public class FlightService extends BasePart
{
    public FlightService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void flightPayment(OnServiceStatus<GlobalResponse2> listener, FlightPaymentRequest request)
    {
        start(getServiceGenerator().createService().flightPayment(request), listener);
    }

    public void sendMessage(OnServiceStatus<GlobalResponse2> listener, FlightPaymentRequest request)
    {
        start(getServiceGenerator().createService().flightSendMessage(request), listener);
    }

    public void userPass(OnServiceStatus<GetUserPassResponse> listener)
    {
        start(getServiceGenerator().createService().getFlightUserPass(), listener);
    }
}
