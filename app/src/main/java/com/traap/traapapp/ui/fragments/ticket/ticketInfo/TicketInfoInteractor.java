package com.traap.traapapp.ui.fragments.ticket.ticketInfo;

import com.traap.traapapp.apiServices.model.getTicketInfo.GetTicketInfoResponse;

/**
 * Created by MahtabAzizi on 11/6/2019.
 */
public interface TicketInfoInteractor
{
    void reservationRequest(OnFinishedTicketInfoListener listener,Long transactionId);


    interface OnFinishedTicketInfoListener {
        void onFinishedTicketInfo(GetTicketInfoResponse response);
        void onErrorTicketInfo(String error);

        void onErrorTicketInfo();
    }

}
