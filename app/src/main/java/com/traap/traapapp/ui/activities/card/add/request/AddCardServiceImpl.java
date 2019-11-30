package com.traap.traapapp.ui.activities.card.add.request;


import org.greenrobot.eventbus.EventBus;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.card.Result;
import com.traap.traapapp.apiServices.model.card.addCard.request.AddCardRequest;
import com.traap.traapapp.models.otherModels.addCard.AddCardModel;

/**
 * Created by Javad.Abadi on 7/16/2018.
 */
public class AddCardServiceImpl implements AddCardIntractor
{
    @Override
    public void findDataAddCardRequest(OnFinishedActiveListener listener, String cardNumber,
                                       String fullName, boolean isFavorite)
    {
        AddCardRequest addCardRequest = new AddCardRequest();
        addCardRequest.setCardNumber(cardNumber);
        addCardRequest.setFullName(fullName);
        addCardRequest.setOrderList(1);


        SingletonService.getInstance().addCardService().addCardService(addCardRequest, new OnServiceStatus<WebServiceClass<Result>>()
        {
            @Override
            public void onReady(WebServiceClass<Result> responseBody)
            {
                try
                {
                    AddCardModel cardModel = new AddCardModel();
                    cardModel.setCard(responseBody.data);
                    EventBus.getDefault().post(cardModel);
                    listener.onFinishedAddCard(true);

                } catch (Exception e)
                {
                    listener.onErrorAddCard(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorAddCard(message);
            }
        });

    }
}
