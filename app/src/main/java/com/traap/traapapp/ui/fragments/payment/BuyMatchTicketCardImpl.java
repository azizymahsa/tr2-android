package com.traap.traapapp.ui.fragments.payment;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.paymentMatch.Viewers;
import com.traap.traapapp.apiServices.model.paymentMatchCard.PaymentMatchCardRequest;
import com.traap.traapapp.apiServices.model.paymentWallet.ResponsePaymentWallet;
import com.traap.traapapp.utilities.Logger;

import java.util.List;

public class BuyMatchTicketCardImpl
{
    public static void BuyMatchTicketCard(String operatorType, int amount, String pin2, int cardId, String cvv2, String expMonth, String expYear,
                                          List<Viewers> viewers, onBuyMatchTicketCardListener listener)
    {
        PaymentMatchCardRequest request = new PaymentMatchCardRequest();
        request.setAmount(amount);
        request.setCvv2(cvv2);
        request.setExpMonth(expMonth);
        request.setExpYear(expYear);
        request.setCardId(cardId);
        request.setPin2(pin2);
        request.setViewers(viewers);

        SingletonService.getInstance().buyCardService().buyMatchTicketByCardService(request, new OnServiceStatus<WebServiceClass<ResponsePaymentWallet>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponsePaymentWallet> response)
            {
                if (response == null || response.data == null)
                {
                    listener.onBuyMatchTicketCardError("خطا در دریافت اطلاعات از سرور!");
                    Logger.e("-onBuyChargeCardError-", "null");
                    return;
                }
                if (response.info.statusCode != 201)
                {
                    listener.onBuyMatchTicketCardError(response.info.message);
                }
                else
                {
                    listener.onBuyMatchTicketCardCompleted(response.info.message);
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onBuyMatchTicketCardError(message);
            }
        });
    }

    public interface onBuyMatchTicketCardListener
    {
        void onBuyMatchTicketCardCompleted(String message);

        void onBuyMatchTicketCardError(String message);
    }
}
