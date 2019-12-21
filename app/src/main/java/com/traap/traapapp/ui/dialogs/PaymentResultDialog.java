package com.traap.traapapp.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.paymentPrintPos.PaymentPrintPosResponse;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Utility;


/**
 * Created by Javad.Abadi on 7/21/2018.
 */
@SuppressLint("ValidFragment")
public class PaymentResultDialog extends DialogFragment implements View.OnClickListener
{
    private Activity activity;
    private WebServiceClass<PaymentPrintPosResponse> posResponse;
    private Dialog dialog;
    private TextView tvDate, tvTrackId, tvAmountDialog, tvNameDialog, tvCardNumberDialog, tvShare,
            tvShareImage, tvMerchantName, tvMerchantPhone, tvTerminalId, tvPaymentId, tvClose, tvShareText, tvTrackText, tvDialogTitle, tvPrintId;
    private RelativeLayout rlShare;
    private String cardNumber, amount, cardName, cardImage, color;
    private ImageView ivCardAlert, ivShetab;


    public PaymentResultDialog(Activity activity, WebServiceClass<PaymentPrintPosResponse> posResponse, String cardNumber, String cardName,
                               String amount, String cardImage, String color)
    {
        this.activity = activity;
        this.posResponse = posResponse;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.cardName = cardName;
        this.cardImage = cardImage;
        this.color = color;

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_card_result_payment);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        tvDate = dialog.findViewById(R.id.tvDate);
        tvTrackId = dialog.findViewById(R.id.tvTrackId);
        tvTrackText = dialog.findViewById(R.id.tvTrackText);
        tvPaymentId = dialog.findViewById(R.id.tvPaymentId);
        tvPrintId = dialog.findViewById(R.id.tvPrintId);
        ivShetab = dialog.findViewById(R.id.ivShetab);
        tvTerminalId = dialog.findViewById(R.id.tvTerminalId);
        tvMerchantPhone = dialog.findViewById(R.id.tvMerchantPhone);
        tvMerchantName = dialog.findViewById(R.id.tvMerchantName);
        tvAmountDialog = dialog.findViewById(R.id.tvAmountDialog);
        tvNameDialog = dialog.findViewById(R.id.tvNameDialog);
        tvNameDialog = dialog.findViewById(R.id.tvNameDialog);
        ivCardAlert = dialog.findViewById(R.id.ivCardAlert);
        tvClose = dialog.findViewById(R.id.tvClose);
        tvCardNumberDialog = dialog.findViewById(R.id.tvCardNumberDialog);
        tvTrackText = dialog.findViewById(R.id.tvTrackText);
        tvShare = dialog.findViewById(R.id.tvShareP);
        tvShareText = dialog.findViewById(R.id.tvShareText);
        tvShareImage = dialog.findViewById(R.id.tvShareImageP);
        tvDialogTitle = dialog.findViewById(R.id.tvDialogTitle);
        tvShare.setOnClickListener(this);
        tvClose.setOnClickListener(this);
        tvShareImage.setOnClickListener(this);

        try
        {
            tvDate.setText(posResponse.data.getCreateDate());

        } catch (Exception e)
        {
            tvDate.setText("نا مشخص");

        }
        try
        {
            tvPrintId.setText(posResponse.data.getPirntId());

        } catch (Exception e)
        {
            tvPrintId.setText("نا مشخص");

        }

        try
        {
            tvTrackId.setText(posResponse.data.getId() + "");
        } catch (Exception e)
        {
            tvTrackId.setText("نا مشخص");
        }
        try
        {
            tvMerchantName.setText(posResponse.data.getMerchantName());

        } catch (Exception e)
        {
            tvMerchantName.setText("نا مشخص");

        }
        try
        {
            tvMerchantName.setText(posResponse.data.getMerchantName());

        } catch (Exception e)
        {
            tvMerchantName.setText("نا مشخص");

        }
        try
        {
            tvMerchantPhone.setText(posResponse.data.getMerchantPhone());

        } catch (Exception e)
        {
            tvMerchantPhone.setText("نا مشخص");

        }
        try
        {
            tvTerminalId.setText(posResponse.data.getTerminalId() + "");

        } catch (Exception e)
        {
            tvTerminalId.setText("نا مشخص");

        }


        tvAmountDialog.setText(amount + " ریال");
        tvNameDialog.setText(cardName);
        tvCardNumberDialog.setText(Utility.cardFormat(cardNumber));
        if (cardNumber.substring(0, 6).equals("003725"))
        {
            tvTrackText.setText("شناسه پیگیری پرداخت:");
            tvDialogTitle.setText("رسید پرداخت وجه شتاکی موفق ");
          //  ivShetab.setImageDrawable(activity.getResources().getDrawable(R.drawable.shetak));
            try
            {
                tvPaymentId.setText(posResponse.data.getRefNo() + "");

            } catch (Exception e)
            {
                tvPaymentId.setText("نا مشخص");

            }

        } else
        {
            tvTrackText.setText("شناسه پیگیری پرداخت:");
            tvDialogTitle.setText("رسید پرداخت وجه شاپرکی موفق");
           // ivShetab.setImageDrawable(activity.getResources().getDrawable(R.drawable.shetab));
            try
            {
                tvPaymentId.setText(posResponse.data.getRefNo() + "");

            } catch (Exception e)
            {
                tvPaymentId.setText("نا مشخص");

            }

        }

        try
        {
            tvCardNumberDialog.setTextColor(Color.parseColor(color));

        } catch (Exception e)
        {
        }

        try
        {
            Glide.with(activity).load(cardImage).into(ivCardAlert);


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
        if (v.getId() == R.id.tvClose)
        {
            dismiss();


        }


        if (v.getId() == R.id.tvShareP)
        {
            String share = "رسید پرداخت" +
                    "\n" +
                    "مبلغ: " +
                    tvAmountDialog.getText().toString() +
                    "\n" +
                    "از کارت: " +
                    Utility.cardStarFormat(cardNumber) +
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
                    "با موفقیت پرداخت گردید." ;

            Utility.share(share);
        }
        if (v.getId() == R.id.tvShareImageP)
        {
            tvCardNumberDialog.setText(Utility.cardStarFormat(cardNumber));
            tvShareImage.setVisibility(View.INVISIBLE);
            tvShare.setVisibility(View.INVISIBLE);
            tvShareText.setVisibility(View.INVISIBLE);
            tvClose.setVisibility(View.INVISIBLE);
            new ScreenShot(dialog.getWindow().getDecorView(), activity);
            tvCardNumberDialog.setText(Utility.cardFormat(cardNumber));
            tvShareImage.setVisibility(View.VISIBLE);
            tvShare.setVisibility(View.VISIBLE);
            tvShareText.setVisibility(View.VISIBLE);
            tvClose.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }


}
