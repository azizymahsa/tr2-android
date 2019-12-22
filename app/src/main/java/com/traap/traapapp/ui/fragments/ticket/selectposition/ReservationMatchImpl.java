package com.traap.traapapp.ui.fragments.ticket.selectposition;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.reservationmatch.ReservationRequest;
import com.traap.traapapp.apiServices.model.reservationmatch.ReservationResponse;
import com.traap.traapapp.ui.fragments.ticket.BuyTicketsFragment;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public class ReservationMatchImpl implements ReservationMatchInteractor
{
    @Override
    public void reservationRequest(OnFinishedReservationListener listener,int matchId, int countViewers, int boxId)
    {
        ReservationRequest request = new ReservationRequest();
        request.setBoxId(boxId);
        request.setMatchId(matchId);
        request.setViewers(countViewers);
        SingletonService.getInstance().getReservation().reservationMatchService(new OnServiceStatus<WebServiceClass<ReservationResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<ReservationResponse> response)
            {
                try{
                    BuyTicketsFragment.buyTicketsFragment.hideLoading();

                    if (response.info.statusCode==200){
                        listener.onFinishedReservation(response.data);
                    }else {
                        listener.onErrorReservation(response.info.message);
                    }
                }catch (Exception e){

                    listener.onError(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                BuyTicketsFragment.buyTicketsFragment.hideLoading();

                listener.onError(message);

            }
        },request);

    }
}