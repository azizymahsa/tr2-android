package ir.traap.tractor.android.ui.activities.card.add.request;


import org.greenrobot.eventbus.EventBus;

import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.card.Result;
import ir.traap.tractor.android.apiServices.model.card.addCard.request.AddCardRequest;
import ir.traap.tractor.android.models.otherModels.addCard.AddCardModel;

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