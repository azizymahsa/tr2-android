package ir.trap.tractor.android.ui.fragments.ticket.selectposition;

import ir.trap.tractor.android.apiServices.model.reservationmatch.ReservationResponse;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public interface ReservationMatchInteractor
{
    void reservationRequest(OnFinishedReservationListener listener,int matchId, int countViewers, int boxId);


    interface OnFinishedReservationListener {
        void onFinishedReservation(ReservationResponse response);
        void onErrorReservation(String error);
    }

}
