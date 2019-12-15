package com.traap.traapapp.ui.fragments.ticket.paymentTicket;

import java.util.List;

import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchResponse;
import com.traap.traapapp.apiServices.model.paymentMatch.Viewers;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public interface PaymentTicketInteractor
{
    void paymentTicketRequest(OnFinishedPaymentTicketListener listener, List<Viewers>  viewers, int amount);


    interface OnFinishedPaymentTicketListener {
        void onFinishedPaymentTicket(PaymentMatchResponse response);
        void onErrorPaymentTicket(String error);

        void onError(String message);
    }
}
