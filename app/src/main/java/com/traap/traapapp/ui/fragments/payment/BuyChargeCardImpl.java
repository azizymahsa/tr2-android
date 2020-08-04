package com.traap.traapapp.ui.fragments.payment;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyChargeCard.BuyChargeCardRequest;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletResponse;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.setEvaluation.request.QuestionItemRequest;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.setEvaluation.request.SetPlayerEvaluationQuestionsRequest;
import com.traap.traapapp.utilities.Logger;

import java.util.List;

public class BuyChargeCardImpl
{
    public static void BuyChargeCard(int operatorType, int simCardType, int typeCharge, int amount, String mobile, String pin2,
                                     int cardId, String cvv2, String expMonth, String expYear, onBuyChargeCardListener listener)
    {
        BuyChargeCardRequest request = new BuyChargeCardRequest();
        request.setAmount(amount);
        request.setCvv2(cvv2);
        request.setExpMonth(expMonth);
        request.setExpYear(expYear);
        request.setMobile(mobile);
        request.setOperatorType(operatorType);
        request.setCardId(cardId);
        request.setPin2(pin2);
        request.setSimCardType(simCardType);
        request.setTypeCharge(typeCharge);

        SingletonService.getInstance().buyCardService().buyChargeCardService(request, new OnServiceStatus<WebServiceClass<BuyChargeWalletResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<BuyChargeWalletResponse> response)
            {
                if (response == null || response.data == null)
                {
                    listener.onBuyChargeCardError("خطا در دریافت اطلاعات از سرور!");
                    Logger.e("-onBuyChargeCardError-", "null");
                    return;
                }
                if (response.info.statusCode != 201)
                {
                    listener.onBuyChargeCardError(response.info.message);
                }
                else
                {
                    listener.onBuyChargeCardCompleted(response.info.message);
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onBuyChargeCardError(message);
            }
        });
    }

    public interface onBuyChargeCardListener
    {
        void onBuyChargeCardCompleted(String message);

        void onBuyChargeCardError(String message);
    }
}
