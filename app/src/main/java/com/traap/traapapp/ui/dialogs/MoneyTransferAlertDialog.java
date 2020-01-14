package com.traap.traapapp.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.traap.traapapp.R;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by MahtabAzizi on 1/14/2020.
 */

@SuppressLint("ValidFragment")
public class MoneyTransferAlertDialog extends DialogFragment implements View.OnClickListener
{
    private MoneyTransferAlertDialog.OnConfirmListener listener;

    private Boolean isRightToLeft = false;

    private View rootView;

    private Dialog dialog;
    private Activity activity;
    private CircularProgressButton btnConfirm, btnCancel;
    private String messages, title;
    private TextView tvMessage, tvTitle,txtAmountDigit,txtAmountChar,txtCustomerName,txtName;
    private String strAmountDigit,strAmountChar,strCustomerName,strName;
    private RelativeLayout rlCancel;
    private Space spaceCancel;
    private Boolean isCancelable;
    private String btnConfirmText = "";
    private String btnCancelText = "";


    public MoneyTransferAlertDialog(Activity activity, String title, String messages, Boolean isCancelable,
                                    MoneyTransferAlertDialog.OnConfirmListener listener, String txtAmountDigit, String txtAmountChar, String txtCustomerName, String txtName)
    {
        this.activity = activity;
        this.listener = listener;
        this.title = title;
        this.messages = messages;
        this.isCancelable = isCancelable;
        this.strAmountDigit = txtAmountDigit;
        this.strAmountChar = txtAmountChar;
        this.strCustomerName = txtCustomerName;
        this.strName = txtName;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_money_transfer);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        tvTitle = dialog.findViewById(R.id.confirm_title);
        tvMessage = dialog.findViewById(R.id.confirm_msessage);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        rlCancel = dialog.findViewById(R.id.rlCancel);
        spaceCancel = dialog.findViewById(R.id.spaceCancel);

        txtAmountDigit = dialog.findViewById(R.id.txtAmountDigit);
        txtAmountChar = dialog.findViewById(R.id.txtAmountChar);
        txtCustomerName = dialog.findViewById(R.id.txtCustomerName);
        txtName = dialog.findViewById(R.id.txtName);

        txtAmountDigit.setText(strAmountDigit);
        txtAmountChar.setText(strAmountChar);
        txtCustomerName.setText(strCustomerName);
        txtName.setText(strName);


        if (!isCancelable)
        {
            rlCancel.setVisibility(View.GONE);
            spaceCancel.setVisibility(View.GONE);
        }
        if (title.equalsIgnoreCase(""))
        {
            tvTitle.setVisibility(View.INVISIBLE);
        }
        else
        {
            tvTitle.setText(title);
        }
        tvMessage.setText(messages);

        if (isRightToLeft)
        {
            tvMessage.setGravity(Gravity.RIGHT);
        }

        if (!btnConfirmText.equalsIgnoreCase(""))
        {
            btnConfirm.setText(btnConfirmText);
        }

        if (!btnCancelText.equalsIgnoreCase(""))
        {
            btnCancel.setText(btnCancelText);
        }

        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        return dialog;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
//    {
//        rootView = inflater.inflate(R.layout.alert_dialog_layout, container, true);
////        getDialog().requestWindowFeature(STYLE_NO_TITLE);
//        setCancelable(isCancelable);
//        getDialog().setCanceledOnTouchOutside(isCancelable);
//
//        return rootView;
//    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnConfirm:
            {
                if (listener != null)
                {
                    listener.onConfirmClick();
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
        public void onConfirmClick();

        public void onCancelClick();
    }


}
