package ir.traap.tractor.android.ui.fragments.ticket.paymentTicket;

import java.util.List;

import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.paymentMatch.PaymentMatchRequest;
import ir.traap.tractor.android.apiServices.model.paymentMatch.PaymentMatchResponse;
import ir.traap.tractor.android.apiServices.model.paymentMatch.Viewers;

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

                    listener.onErrorPaymentTicket(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorPaymentTicket(message);

            }
        }, request);

    }

}


