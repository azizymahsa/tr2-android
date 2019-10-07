package ir.trap.tractor.android.apiServices.part;


import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.GlobalResponse3;
import ir.trap.tractor.android.apiServices.model.tourism.GetUserPassResponse;
import ir.trap.tractor.android.apiServices.model.tourism.bus.getMessageBus.request.BusSendMessage;
import ir.trap.tractor.android.apiServices.model.tourism.bus.getPaymentBus.request.RequestBusPayment;

/**
 * Created by MahsaAzizi on 9/25/2019.
 */
public class BusService extends BasePart
{
    public BusService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }


    public void busBooking(OnServiceStatus<GlobalResponse3> listener, RequestBusPayment request)
    {//prev factor
        start(getServiceGenerator().createService().busBooking(request), listener);
    }

    public void sendMessage(OnServiceStatus<GlobalResponse3> listener, BusSendMessage request)
    {// Next factor GDS
        start(getServiceGenerator().createService().busSendMessage(request), listener);
    }

    public void userPass(OnServiceStatus<GetUserPassResponse> listener)
    {//AccountInfo
        start(getServiceGenerator().createService().getBusUserPass(), listener);
    }
}
