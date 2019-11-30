package com.traap.traapapp.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.traap.traapapp.R;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Utility;
import library.android.eniac.utility.GlideApp;

/**
 * Created by Javad.Abadi on 7/21/2018.
 */
@SuppressLint("ValidFragment")
public class ResultBuyCharge extends DialogFragment implements View.OnClickListener
{
    private Activity activity;
    private Dialog dialog;
    private TextView tvDate, tvTrackId, tvAmountDialog, tvNameDialog, tvCardNumberDialog, tvDialogTitle,
            tvShareP, tvShareImageP, tvClose, tvMobileNumber, tvShareText, tvPackTitle, tvTypeTitle, tvPaymentId;

    private RelativeLayout rlShare;
    private String cardNumber, amount, cardName, date, trackId, cardImage, color, mobileNumber, title, paymentId;
    private LinearLayout llShopName, llShopPhone;
    private boolean isPack;
    private View v1, v2;
    private ImageView ivCardAlert;


    public ResultBuyCharge(Activity activity, String date, String trackId, String cardNumber, String cardName, String amount,
                           boolean isPack, String cardImage, String color, String mobileNumber, String title, String paymentId)
    {
        this.activity = activity;
        this.date = date;
        this.trackId = trackId;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.cardName = cardName;
        this.isPack = isPack;
        this.cardImage = cardImage;
        this.color = color;
        this.paymentId = paymentId;
        this.mobileNumber = mobileNumber;
        this.title = title;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_card_result_charge);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        tvDate = dialog.findViewById(R.id.tvDate);
        tvClose = dialog.findViewById(R.id.tvClose);
        tvTrackId = dialog.findViewById(R.id.tvTrackId);
        tvPaymentId = dialog.findViewById(R.id.tvPaymentId);
        tvAmountDialog = dialog.findViewById(R.id.tvAmountDialog);
        tvNameDialog = dialog.findViewById(R.id.tvNameDialog);
        ivCardAlert = dialog.findViewById(R.id.ivCardAlert);
        tvCardNumberDialog = dialog.findViewById(R.id.tvCardNumberDialog);
        llShopName = dialog.findViewById(R.id.llShopName);
        tvTypeTitle = dialog.findViewById(R.id.tvTypeTitle);
        llShopPhone = dialog.findViewById(R.id.llShopPhone);
        tvPackTitle = dialog.findViewById(R.id.tvPackTitle);
        tvShareP = dialog.findViewById(R.id.tvShareP);
        tvShareImageP = dialog.findViewById(R.id.tvShareImageP);
        tvDialogTitle = dialog.findViewById(R.id.tvDialogTitle);
        tvMobileNumber = dialog.findViewById(R.id.tvMobileNumber);
        tvShareText = dialog.findViewById(R.id.tvShareText);
        v2 = dialog.findViewById(R.id.v2);
        v1 = dialog.findViewById(R.id.v1);
        if (isPack)
        {
            tvDialogTitle.setText("خرید بسته با موفقیت انجام شد.");
            tvTypeTitle.setText("عنوان بسته:");
        }
        else
        {
            tvDialogTitle.setText("خرید شارژ با موفقیت انجام شد.");
            tvTypeTitle.setText("عنوان شارژ:");

        }
        llShopPhone.setVisibility(View.GONE);
        llShopName.setVisibility(View.GONE);
        v2.setVisibility(View.GONE);
        v1.setVisibility(View.GONE);
        tvShareImageP.setOnClickListener(this);
        tvShareP.setOnClickListener(this);
        tvClose.setOnClickListener(this);

        try
        {
            tvDate.setText(date);

        }
        catch (Exception e)
        {
            tvDate.setText("نا مشخص");

        }
        if (!cardNumber.startsWith("003725"))
        {
            try
            {
                tvPaymentId.setText(paymentId);

            }
            catch (Exception e)
            {
                tvPaymentId.setText("نا مشخص");

            }
        }
        else
        {
            dialog.findViewById(R.id.llPaymentId).setVisibility(View.GONE);
            dialog.findViewById(R.id.vPaymentId).setVisibility(View.GONE);
        }

        try
        {
            tvTrackId.setText(trackId);

        } catch (Exception e)
        {
            tvTrackId.setText("نا مشخص");

        }

        tvAmountDialog.setText(amount + " ریال");
        tvNameDialog.setText(cardName);
        tvCardNumberDialog.setText(Utility.cardFormat(cardNumber));
        tvMobileNumber.setText(mobileNumber);
        tvPackTitle.setText(title);

        try
        {
            tvCardNumberDialog.setTextColor(Color.parseColor(color));

        } catch (Exception e)
        {
        }

        try
        {
            GlideApp.with(activity).load(cardImage).into(ivCardAlert);


        } catch (Exception e)
        {

        }

        return dialog;
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvClose:
                dismiss();
                break;
            case R.id.tvShareImageP:
//                tvCardNumberDialog.setText(Utility.cardStarFormat(cardNumber));
                tvCardNumberDialog.setText(cardNumber);
                tvShareImageP.setVisibility(View.INVISIBLE);
                tvShareP.setVisibility(View.INVISIBLE);
                tvShareText.setVisibility(View.INVISIBLE);
                tvClose.setVisibility(View.INVISIBLE);
                new ScreenShot(dialog.getWindow().getDecorView(), activity);
                tvCardNumberDialog.setText(Utility.cardFormat(cardNumber));
                tvShareImageP.setVisibility(View.VISIBLE);
                tvShareP.setVisibility(View.VISIBLE);
                tvShareText.setVisibility(View.VISIBLE);
                tvClose.setVisibility(View.VISIBLE);

                break;
            case R.id.tvShareP:
                String share = (isPack ? "رسید خرید بسته" : "رسید خرید شارژ") +
                        "\n" +
                        "شماره موبایل: " +
                        mobileNumber +
                        "\n" +
                        "مبلغ: " +
                        tvAmountDialog.getText().toString() +
                        "\n" +
                        "از کارت: " +
//                        Utility.cardStarFormat(cardNumber) +
                        cardNumber +
                        "\n" +
                        "متعلق به: " +
                        tvNameDialog.getText().toString() +
                        "\n" +
                        "با شماره پیگیری: " +
                        tvTrackId.getText().toString() +
                        "\n" +
                        "در تاریخ: " +
                        tvDate.getText().toString() +
                        "\n" +
                        "با موفقیت پرداخت گردید." +
                        "\n" +
                        R.string.loyalBank +
                        "\n" +
                        activity.getString(R.string.wwwloyalbank);
                Utility.share(share);
                break;
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }


}
