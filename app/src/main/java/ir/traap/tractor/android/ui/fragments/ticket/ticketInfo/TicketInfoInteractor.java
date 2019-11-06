package ir.traap.tractor.android.ui.fragments.ticket.ticketInfo;

import ir.traap.tractor.android.apiServices.model.getTicketInfo.GetTicketInfoResponse;

/**
 * Created by MahtabAzizi on 11/6/2019.
 */
public interface TicketInfoInteractor
{
    void reservationRequest(OnFinishedTicketInfoListener listener,int transactionId);


    interface OnFinishedTicketInfoListener {
        void onFinishedTicketInfo(GetTicketInfoResponse response);
        void onErrorTicketInfo(String error);
    }

}
