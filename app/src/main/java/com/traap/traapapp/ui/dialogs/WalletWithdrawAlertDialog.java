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
 * Created by Javad.Abadi on 6/24/2019.
 */
@SuppressLint("ValidFragment")
public class WalletWithdrawAlertDialog extends DialogFragment implements View.OnClickListener
{
    private OnConfirmListener listener;

    private Boolean isRightToLeft = false;

    private View rootView;

    private Dialog dialog;
    private Activity activity;
    private CircularProgressButton btnConfirm, btnCancel;
    private String messages, title;
    private TextView tvMessage, tvTitle,txtAmountDigit,txtAmountChar,txtNumberShaba,txtName;
    private String strAmountDigit,strAmountChar,strNumberShaba,strName;
    private RelativeLayout rlCancel;
    private Space spaceCancel;
    private Boolean isCancelable;
    private String btnConfirmText = "";
    private String btnCancelText = "";


    public WalletWithdrawAlertDialog(Activity activity, String title, String messages, Boolean isCancelable,
                                     OnConfirmListener listener,String txtAmountDigit,String txtAmountChar,String txtNumberShaba,String txtName)
    {
        this.activity = activity;
        this.listener = listener;
        this.title = title;
        this.messages = messages;
        this.isCancelable = isCancelable;
        this.strAmountDigit = txtAmountDigit;
        this.strAmountChar = txtAmountChar;
        this.strNumberShaba = txtNumberShaba;
        this.strName = txtName;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_wallet_withdraw_layout);
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
        txtNumberShaba = dialog.findViewById(R.id.txtNumberShaba);
        txtName = dialog.findViewById(R.id.txtName);

        txtAmountDigit.setText(strAmountDigit);
        txtAmountChar.setText(strAmountChar);
        txtNumberShaba.setText(strNumberShaba);
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
