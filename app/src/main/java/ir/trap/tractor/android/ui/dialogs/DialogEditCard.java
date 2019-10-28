package ir.trap.tractor.android.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputLayout;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.card.Result;
import ir.trap.tractor.android.ui.fragments.favoriteCard.FavoriteCardActionView;
import ir.trap.tractor.android.utilities.Logger;
import ir.trap.tractor.android.utilities.Tools;
import ir.trap.tractor.android.utilities.Utility;
import library.android.eniac.base.BaseDialog;

/**
 * Created by Javad.Abadi on 7/23/2018.
 */
@SuppressLint("ValidFragment")
public class DialogEditCard extends BaseDialog implements View.OnClickListener
{
    private Context context;
    private Dialog dialog;
    private TextInputLayout etLayoutCardNameEdit, etLayoutNumberCardEdit;
    private EditText etCardNameEdit, etNumberCardEdit;
    private Result item;
//    private LottieAnimationView lottieViewEdit;
    private CircularProgressButton btnConfirmEdit, btnCancelEdit;
    private FavoriteCardActionView mainView;
//    private ArchiveView archiveView;
    private int position;
//    boolean isHappy;
//    boolean isDestination = false;
    private LinearLayout llNumberCardEdit;
    private TextView tvTitle;
    private String title;

    public DialogEditCard(Context context, Result item, FavoriteCardActionView mainView, int position)
    {
        this.context = context;
        this.item = item;
        this.mainView = mainView;
        this.position = position;

    }

//    public DialogEditCard(Context context, ArchiveCardDBModel item, ArchiveView archiveView, long dbPosition, int position)
//    {
//        this.context = context;
//        this.item = item;
//        this.archiveView = archiveView;
//        this.dbPosition = dbPosition;
//        this.position = position;
//        isMain = false;
//
//    }
//
//    public DialogEditCard(Context context, MainView mainView)
//    {
//        this.context = context;
//        this.mainView = mainView;
//        isDestination = true;
//
//    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(context, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_card_edit);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        etLayoutNumberCardEdit = dialog.findViewById(R.id.etLayoutNumberCardEdit);
        etLayoutCardNameEdit = dialog.findViewById(R.id.etLayoutCardNameEdit);
        etNumberCardEdit = dialog.findViewById(R.id.etNumberCardEdit);
        etCardNameEdit = dialog.findViewById(R.id.etCardNameEdit);
//        lottieViewEdit = dialog.findViewById(R.id.lottieViewEdit);
        btnConfirmEdit = dialog.findViewById(R.id.btnConfirmEdit);
        btnCancelEdit = dialog.findViewById(R.id.btnCancelEdit);
        tvTitle = dialog.findViewById(R.id.tvTitle);
        llNumberCardEdit = dialog.findViewById(R.id.llNumberCardEdit);
        etLayoutCardNameEdit.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        etLayoutNumberCardEdit.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
//        lottieViewEdit.setMaxFrame(44);
        btnConfirmEdit.setOnClickListener(this);
        btnCancelEdit.setOnClickListener(this);

        if (!TextUtils.isEmpty(title))
            tvTitle.setText(title);

//        etNumberCardEdit.setText(Utility.cardFormat(item.getCardNumber()));
        etCardNameEdit.setText(item.getFullName());

        String cardNumberCheck = item.getBankBin();

//        if (cardNumberCheck.equals(TrapConfig.HappyBaseCardNo))
//        {
//            isHappy = true;
//        }
//        else
//        {
//            isHappy = false;
//        }
        if (item.getIsMainCard())
        {
            llNumberCardEdit.setVisibility(View.GONE);
        }
        else
        {
            llNumberCardEdit.setVisibility(View.VISIBLE);
        }

        return dialog;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnConfirmEdit:
            {

                Logger.e("text", etCardNameEdit.getText().toString());
                Logger.e("text2", etCardNameEdit.getText().toString().length() + "");
//                if (!Utility.isNetworkAvailable())
//                {
//                    dismiss();
////                    mainView.onInternetAlert();
//
//                    return;
//
//                }
                if (etNumberCardEdit.getText().toString().replaceAll("-", "").replaceAll("_", "").length() != 16)
                {
                    Tools.showToast(context, context.getString(R.string.enter_card_number), R.color.red);
                    return;
                }

                if (TextUtils.isEmpty(etCardNameEdit.getText().toString()))
                {
                    Tools.showToast(context, context.getString(R.string.enter_name), R.color.red);
                    return;
                }

                if (TextUtils.isEmpty(etNumberCardEdit.getText().toString()))
                {
                    Tools.showToast(context, context.getString(R.string.enter_card_number), R.color.red);
                    return;
                }
                if (etCardNameEdit.getText().toString().length() < 2 || Utility.containsNumber(etCardNameEdit.getText().toString()))
                {
                    Tools.showToast(context, "لطفا نام و نام خانوادگی را درست وارد نمایید.", R.color.red);

                    return;
                }

                item.setCardNumber(etNumberCardEdit.getText().toString().replaceAll("-", ""));
                item.setFullName(etCardNameEdit.getText().toString().trim());
                mainView.onEditCard(item, position);

                dismiss();

                break;
            }
            case R.id.btnCancelEdit:
            {
                dismiss();
                break;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }


    public void setCardPan(String cardPan)
    {
        etNumberCardEdit.setText(cardPan);
//        getFullName(cardPan);
    }

    public void setCardExpireDate(String date)
    {
        //  etExpireDate.setText(date);
    }


//    public void getFullName(String pan)
//    {
//        mainView.showProgress();
//
//        GetFullNameCardRequest request = new GetFullNameCardRequest();
//        request.setPan(pan);
//        request.setUserId(Prefs.getInt("userId", 0));
//        SingletonService.getInstance().getAddCard().getFullNameCard(new OnServiceStatus<GetFullNameCardResponse>()
//        {
//            @Override
//            public void onReady(GetFullNameCardResponse getFullNameCardResponse)
//            {
//                try
//                {
//                    mainView.hideProgress();
//                    if (getFullNameCardResponse.getServiceMessage().getCode() == 200)
//                    {
//                        etCardNameEdit.setText(getFullNameCardResponse.getFirstName() + " " + getFullNameCardResponse.getLastName());
//                    } else
//                    {
//                        //  AddCardActivity.this.onError(getFullNameCardResponse.getServiceMessage().getMessage(),this.getClass().getSimpleName(), Config.showClassNameInMessage);
//                    }
//                } catch (Exception e)
//                {
//                    //  AddCardActivity.this.onError(e.getMessage(),this.getClass().getSimpleName(), Config.showClassNameInException);
//                }
//
//            }
//
//            @Override
//            public void onError(String message)
//            {
//                mainView.hideProgress();
//
//                // AddCardActivity.this.onError(message,this.getClass().getSimpleName(), Config.showClassNameInException);
//
//            }
//        }, request);
//
//
//    }
}
