package com.traap.traapapp.ui.fragments.ticket.ticketInfo;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getTicketInfo.GetTicketInfoRequest;
import com.traap.traapapp.apiServices.model.getTicketInfo.GetTicketInfoResponse;

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

                try
                {
                    if (response.info.statusCode == 200)
                    {
                        if (response.data.getResults().isEmpty())
                        {
                            listener.onErrorTicketInfo("پرداخت ناموفق!");
                        }
                        else
                        {
                            listener.onFinishedTicketInfo(response.data);
                        }
                    }
                    else
                    {
                        listener.onErrorTicketInfo(response.info.message);
                    }
                } catch (Exception e)
                {

                    listener.onErrorTicketInfo(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorTicketInfo(message);

            }
        }, request);
    }
}
