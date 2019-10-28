package ir.trap.tractor.android.ui.activities.card.add.request;

//import service.model.addcard.response.AddCardResponse;

/**
 * Created by Javad.Abadi on 7/16/2018.
 */
public interface AddCardIntractor
{
    interface OnFinishedActiveListener
    {
        void onFinishedAddCard(Boolean isSuccess);

        void onErrorAddCard(String error);
    }

    void findDataAddCardRequest(OnFinishedActiveListener listener, String cardNumber,
                                String fullName, boolean isFavorite);
}
