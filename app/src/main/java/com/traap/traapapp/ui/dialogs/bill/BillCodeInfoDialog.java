package com.traap.traapapp.ui.dialogs.bill;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.billCode.BillCodeResponse;
import com.traap.traapapp.utilities.Utility;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by MahtabAzizi on 6/16/2020.
 */
@SuppressLint("ValidFragment")
public class BillCodeInfoDialog extends DialogFragment implements View.OnClickListener
{
    private BillCodeResponse response;
    private BillCodeInfoDialog.OnConfirmListener listener;

    private Boolean isRightToLeft = false;

    private View rootView;

    private Dialog dialog;
    private Activity activity;
    private CircularProgressButton btnConfirm, btnCancel;
    private TextView tvAmount,tvDate ,tvBillCode,tvBillPay;

    private String btnConfirmText = "";
    private String btnCancelText = "";


    public BillCodeInfoDialog(Activity activity, BillCodeResponse response,
                              BillCodeInfoDialog.OnConfirmListener listener)
    {
        this.activity = activity;
        this.listener = listener;
        this.response = response;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_bill_detail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        tvAmount = dialog.findViewById(R.id.tvAmount);
        tvDate = dialog.findViewById(R.id.tvDate);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        tvBillCode = dialog.findViewById(R.id.tvBillCode);
        tvBillPay = dialog.findViewById(R.id.tvBillPay);

        btnConfirm.setText("ادامه");

        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        setData(response);

        return dialog;
    }

    private void setData(BillCodeResponse response)
    {
        tvAmount.setText("مبلغ: "+ Utility.priceFormat(response.getAmount())+" ریال");
        tvDate.setText("تا تاریخ: "+ response.getRemainPayDate());
        tvBillCode.setText("شناسه قبض: "+ response.getBillCode());
        tvBillPay.setText("شناسه پرداخت: "+ response.getPayCode());
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnConfirm:
            {
                if (listener != null)
                {
                    listener.onConfirmClick(response);
                }
                dismiss();
                break;
            }
            case R.id.btnCancel:
            {
                listener.onCancelClick();
                dismiss();
                break;
            }
        }
    }

    public interface OnConfirmListener
    {
        public void onConfirmClick(BillCodeResponse response);

        public void onCancelClick();
    }


}

