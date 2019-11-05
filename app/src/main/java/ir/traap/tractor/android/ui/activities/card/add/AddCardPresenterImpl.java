package ir.traap.tractor.android.ui.activities.card.add;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.singleton.SingletonContext;
import ir.traap.tractor.android.ui.activities.card.add.request.AddCardServiceImpl;
import ir.traap.tractor.android.utilities.Utility;


/**
 * Created by Javad.Abadi on 7/16/2018.
 */
public class AddCardPresenterImpl
        implements AddCardPresenter, View.OnClickListener, AddCardServiceImpl.OnFinishedActiveListener
{
    private AddCardView addCardView;
    private String cardNumber, fullName;
    private boolean isFavorite;
    private AddCardServiceImpl addCardService;
    private Context context;
    private View view;

    public AddCardPresenterImpl(AddCardView addCardView, AddCardServiceImpl addCardService, View view)
    {
        this.addCardView = addCardView;
        this.addCardService = addCardService;
        this.view = view;
        context = SingletonContext.getInstance().getContext();
    }

    @Override
    public void onResume()
    {

    }

    @Override
    public void onDestroy()
    {

    }

    @Override
    public void onStart()
    {

    }

    @Override
    public void onBack()
    {

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnConfirm:
                addCardView.getData();
                if (TextUtils.isEmpty(fullName))
                {
                    addCardView.onError(context.getString(R.string.enter_name));
                    Utility.hideSoftKeyboard(view, context);

                    return;
                }
                if (fullName.length() < 2 || Utility.containsNumber(fullName))
                {

                    addCardView.onError("لطفا نام و نام خانوادگی را درست وارد نمایید.");
                    Utility.hideSoftKeyboard(view, context);
                    return;
                }
                if (TextUtils.isEmpty(cardNumber) || cardNumber.length() != 16)
                {
                    addCardView.onError(context.getString(R.string.enter_card_number));
                    Utility.hideSoftKeyboard(view, context);
                    return;
                }


                if (!Utility.CheckCartDigit(cardNumber))
                {
                    addCardView.onError("شماره کارت صحیح نمی باشد.");
                    Utility.hideSoftKeyboard(view, context);
                    return;
                }

//                List<ArchiveCardDBModel> archiveCardDBModels = ArchiveCardDBModel.listAll(ArchiveCardDBModel.class);
//                if (contains(archiveCardDBModels, cardNumber))
//                {
//                    addCardView.onError("این کارت قبلا ذخیره شده است.", this.getClass().getSimpleName(), TrapConfig.showClassNameInMessage);
//                    Utility.hideSoftKeyboard(view, context);
//
//                    return;
//                }
                if (!Utility.isNetworkAvailable())
                {

                    addCardView.onError("اینترنت شما قطع و یا از دسترس خارج می باشد!");
                    Utility.hideSoftKeyboard(view, context);

                    return;
                }
                addCardView.showLoading();
                addCardService.findDataAddCardRequest(this, cardNumber, fullName, isFavorite);

                break;
            case R.id.imgBack:
                addCardView.onFinish(Activity.RESULT_CANCELED);
                break;
        }

    }

//    boolean contains(List<ArchiveCardDBModel> list, String name)
//    {
//        for (ArchiveCardDBModel item : list)
//        {
//            if (item.getNumber() != null)
//            {
//                if (item.getNumber().equals(name))
//                {
//                    return true;
//                }
//            }
//
//        }
//        return false;
//    }


    @Override
    public void setCardDetail(String cardNumber, String fullName, boolean isFavorite)
    {
        this.cardNumber = cardNumber;
        this.fullName = fullName;
        this.isFavorite = isFavorite;


    }

    @Override
    public void onFinishedAddCard(Boolean isSuccess)
    {
        addCardView.hideLoading();
        if (isSuccess)
        {
            addCardView.onFinish(Activity.RESULT_OK);
        }
        else
        {
            addCardView.onError("خطا در ارسال کارت!");
        }
//        if (addCardResponse.getServiceMessage().getCode() == 200)
//        {
////            SingletonCard.getInstance().setNewCard(cardNumber);
//
////            SingletonRequest.getInstance().setNeedRequest(true, false);
//
////            ArchiveCardDBModel dbModel = new ArchiveCardDBModel();
////            dbModel.setFavorite(isFavorite);
////            dbModel.setExpireMonth(expireMonth);
////            dbModel.setExpireYear(expireYear);
////            dbModel.setfullName(fullName);
////            dbModel.setNumber(cardNumber);
////            dbModel.setCvv(cvv);
////            dbModel.save();
//            Utility.hideSoftKeyboard(view, context);
//            addCardView.onFinish(Activity.RESULT_OK);
//
//        } else
//            addCardView.onError(addCardResponse.getServiceMessage().getMessage(), this.getClass().getSimpleName(), TrapConfig.showClassNameInMessage);
        Utility.hideSoftKeyboard(view, context);


    }


    @Override
    public void onErrorAddCard(String error)
    {
        addCardView.hideLoading();
        addCardView.onError(error);
        Utility.hideSoftKeyboard(view, context);


    }
}
