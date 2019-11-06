package ir.traap.tractor.android.ui.fragments.ticket.selectposition;

import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.reservationmatch.ReservationRequest;
import ir.traap.tractor.android.apiServices.model.reservationmatch.ReservationResponse;
import ir.traap.tractor.android.ui.fragments.ticket.BuyTickets;

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
                    BuyTickets.buyTickets.hideLoading();

                    if (response.info.statusCode==200){
                        listener.onFinishedReservation(response.data);
                    }else {
                        listener.onErrorReservation(response.info.message);
                    }
                }catch (Exception e){

                    listener.onErrorReservation(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                BuyTickets.buyTickets.hideLoading();

                listener.onErrorReservation(message);

            }
        },request);

    }

/*    @Override
    public void findDataIrancellBuyRequest(PaymentActionView listener, String price, int simcardType, int operatorType, int typeCharge, String password
            , String mobile, String cvv2, String expDate,Integer cardId)
    {
        MobileChargeRequest request = new MobileChargeRequest();
        request.setPin(password);
        SingletonService.getInstance().getMobileCharge().MobileChargeService(new OnServiceStatus<WebServiceClass<MobileChargeResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<MobileChargeResponse> response)
            {
                try
                {
                    if (response.info.statusCode==200)
                    {
                        listener.onPaymentChargeSimCard(response.data, mobile);
                    }else {
                        listener.onErrorCharge(response.info.message);
                    }

                }
                catch (Exception e)
                {
                    listener.onErrorCharge(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorCharge(message);
            }
        }, request);
    }*/
}