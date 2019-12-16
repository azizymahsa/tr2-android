package com.traap.traapapp.ui.fragments.ticket.paymentTicket;

import java.util.List;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchRequest;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchResponse;
import com.traap.traapapp.apiServices.model.paymentMatch.Viewers;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public class PaymentTicketImpl implements PaymentTicketInteractor
{
    @Override
    public void paymentTicketRequest(OnFinishedPaymentTicketListener listener, List<Viewers> viewers, int amount)
    {
        PaymentMatchRequest request = new PaymentMatchRequest();
        request.setAmount(amount);
        request.setViewers(viewers);
        SingletonService.getInstance().paymentMatch().PaymentMatchService(new OnServiceStatus<WebServiceClass<PaymentMatchResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<PaymentMatchResponse> response)
            {
                try
                {
                    if (response.info.statusCode == 200)
                    {
                        listener.onFinishedPaymentTicket(response.data);
                    }
                    else
                    {
                        listener.onErrorPaymentTicket(response.info.message);
                    }

                }
                catch (Exception e)
                {

                    listener.onError(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onError(message);

            }
        }, request);

    }

}


