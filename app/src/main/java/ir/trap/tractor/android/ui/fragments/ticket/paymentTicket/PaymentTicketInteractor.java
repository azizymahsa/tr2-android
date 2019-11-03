package ir.trap.tractor.android.ui.fragments.ticket.paymentTicket;

import java.util.List;

import ir.trap.tractor.android.apiServices.model.paymentMatch.PaymentMatchResponse;
import ir.trap.tractor.android.apiServices.model.paymentMatch.Viewers;
import ir.trap.tractor.android.apiServices.model.reservationmatch.ReservationResponse;
import ir.trap.tractor.android.ui.fragments.ticket.selectposition.ReservationMatchInteractor;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public interface PaymentTicketInteractor
{
    void paymentTicketRequest(OnFinishedPaymentTicketListener listener, List<Viewers>  viewers, int amount);


    interface OnFinishedPaymentTicketListener {
        void onFinishedPaymentTicket(PaymentMatchResponse response);
        void onErrorPaymentTicket(String error);
    }
}
