package ir.traap.tractor.android.ui.fragments.ticket.ticketInfo;

import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getTicketInfo.GetTicketInfoRequest;
import ir.traap.tractor.android.apiServices.model.getTicketInfo.GetTicketInfoResponse;

/**
 * Created by MahtabAzizi on 11/6/2019.
 */
public class TicketInfoImpl implements TicketInfoInteractor
{
    @Override
    public void reservationRequest(OnFinishedTicketInfoListener listener, int transactionId)
    {
        GetTicketInfoRequest request = new GetTicketInfoRequest();
        request.setTransactionId(transactionId);

        SingletonService.getInstance().getTicketInfoService().getTicketInfoService(new OnServiceStatus<WebServiceClass<GetTicketInfoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetTicketInfoResponse> response)
            {
                try{

                    if (response.info.statusCode==200){
                        listener.onFinishedTicketInfo(response.data);
                    }else {
                        listener.onErrorTicketInfo(response.info.message);
                    }
                }catch (Exception e){

                    listener.onErrorTicketInfo(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorTicketInfo(message);

            }
        },request);
    }
}
