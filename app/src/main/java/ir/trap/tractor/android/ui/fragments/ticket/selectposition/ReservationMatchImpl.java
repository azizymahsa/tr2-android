package ir.trap.tractor.android.ui.fragments.ticket.selectposition;

import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.reservationmatch.ReservationRequest;
import ir.trap.tractor.android.apiServices.model.reservationmatch.ReservationResponse;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public class ReservationMatchImpl implements ReservationMatchInteractor
{
    @Override
    public void reservationRequest(int matchId, int countViewers, int boxId)
    {
        ReservationRequest request = new ReservationRequest();
        request.setBoxId(boxId);
        request.setMatchId(matchId);
        request.setViewers(countViewers);
        SingletonService.getInstance().getReservation().reservationMatchService(new OnServiceStatus<WebServiceClass<ReservationResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<ReservationResponse> reservationResponseWebServiceClass)
            {
                try{

                }catch (Exception e){

                }
            }

            @Override
            public void onError(String message)
            {

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