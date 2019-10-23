package ir.trap.tractor.android.ui.activities.card.add.request;


import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.card.addCard.request.AddCardRequest;
import okhttp3.ResponseBody;

/**
 * Created by Javad.Abadi on 7/16/2018.
 */
public class AddCardServiceImpl implements AddCardIntractor
{
    @Override
    public void findDataAddCardRequest(OnFinishedActiveListener listener, String cardNumber, int cvv, String expirationDateMonth,
                                       String expirationDateYear, String fullName, boolean isFavorite, int userId)
    {
        String mExpirationDateMonth = expirationDateMonth.replaceAll("-", "").replaceAll("_", "").replaceAll(" ", "");
        String mExpirationDateYear = expirationDateYear.replaceAll("-", "").replaceAll("_", "").replaceAll(" ", "");

        if (mExpirationDateMonth.equalsIgnoreCase(""))
        {
            mExpirationDateMonth = "0";
        }

        if (mExpirationDateYear.equalsIgnoreCase(""))
        {
            mExpirationDateYear = "0";
        }



        AddCardRequest addCardRequest = new AddCardRequest();
        addCardRequest.setCardNumber(cardNumber);
        addCardRequest.setExpirationDateMonth(mExpirationDateMonth);
        addCardRequest.setExpirationDateYear(mExpirationDateYear);
        addCardRequest.setFullName(fullName);
        addCardRequest.setOrderList(1);


        SingletonService.getInstance().addCardService().addCardService(addCardRequest, new OnServiceStatus<WebServiceClass<Object>>()
        {
            @Override
            public void onReady(WebServiceClass<Object> responseBody)
            {
                try
                {
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
