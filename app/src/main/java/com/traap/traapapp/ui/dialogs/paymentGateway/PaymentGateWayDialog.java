package com.traap.traapapp.ui.dialogs.paymentGateway;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import com.traap.traapapp.R;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;

@SuppressLint("ValidFragment")
public class PaymentGateWayDialog extends DialogFragment implements View.OnClickListener
{
    private MessageAlertDialog.OnConfirmListener listener;

    private Boolean isRightToLeft = false;

    private View rootView;

    private Dialog dialog;
    private Activity activity;
    private CircularProgressButton btnConfirm;
    private String messages, title;
    private TextView tvMessage, tvTitle;
    //private RelativeLayout rlCancel;
   // private Space spaceCancel;
    private Boolean isCancelable;
    private String btnConfirmText = "";
    private String btnCancelText = "";


    public PaymentGateWayDialog(Activity activity, String title, String messages, Boolean isCancelable,
                                MessageAlertDialog.OnConfirmListener listener)
    {
        this.activity = activity;
        this.listener = listener;
        this.title = title;
        this.messages = messages;
        this.isCancelable = isCancelable;
    }

    public PaymentGateWayDialog(Activity activity, String title, String messages)
    {
        this.activity = activity;
        this.listener = null;
        this.title = title;
        this.messages = messages;
        this.isCancelable = false;
    }

    public PaymentGateWayDialog(Activity activity, String title, String messages, Boolean isCancelable,
                                String btnConfirmText, String btnCancelText, MessageAlertDialog.OnConfirmListener listener)
    {
        this.activity = activity;
        this.listener = listener;
        this.title = title;
        this.messages = messages;
        this.isCancelable = isCancelable;
        this.btnConfirmText = btnConfirmText;
        this.btnCancelText = btnCancelText;
    }

    public PaymentGateWayDialog(Activity activity, String title, String messages, Boolean isCancelable,
                                String btnConfirmText, String btnCancelText, Boolean isRightToLeft, MessageAlertDialog.OnConfirmListener listener)
    {
        this.activity = activity;
        this.listener = listener;
        this.title = title;
        this.messages = messages;
        this.isCancelable = isCancelable;
        this.btnConfirmText = btnConfirmText;
        this.btnCancelText = btnCancelText;
        this.isRightToLeft = isRightToLeft;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.dialog_payment_gateway);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        tvTitle = dialog.findViewById(R.id.confirm_title);
        tvMessage = dialog.findViewById(R.id.confirm_msessage);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);
       // btnCancel = dialog.findViewById(R.id.btnCancel);
       // rlCancel = dialog.findViewById(R.id.rlCancel);
      //  spaceCancel = dialog.findViewById(R.id.spaceCancel);
        dialog.getWindow().setGravity(Gravity.BOTTOM);

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

        btnConfirm.setOnClickListener(this);
      //  btnCancel.setOnClickListener(this);

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
            /*case R.id.btnCancel:
            {
                listener.onCancelClick();
                dismiss();
                break;
            }*/
        }
    }

    public interface OnConfirmListener
    {
        public void onConfirmClick();

        public void onCancelClick();
    }


}
