package ir.traap.tractor.android.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ir.traap.tractor.android.R;

/**
 * Created by Javad.Abadi on 6/24/2019.
 */
@SuppressLint("ValidFragment")
public class MessageAlertDialog extends DialogFragment implements View.OnClickListener
{
    private OnConfirmListener listener;

    private View rootView;

    private Dialog dialog;
    private Activity activity;
    private CircularProgressButton btnConfirm, btnCancel;
    private String messages, title;
    private TextView tvMessage, tvTitle;
    private RelativeLayout rlCancel;
    private Boolean isCancelable;
    private String btnConfirmText = "";
    private String btnCancelText = "";


    public MessageAlertDialog(Activity activity, String title, String messages, Boolean isCancelable,
                              OnConfirmListener listener)
    {
        this.activity = activity;
        this.listener = listener;
        this.title = title;
        this.messages = messages;
        this.isCancelable = isCancelable;
    }

    public MessageAlertDialog(Activity activity, String title, String messages)
    {
        this.activity = activity;
        this.listener = null;
        this.title = title;
        this.messages = messages;
        this.isCancelable = false;
    }

    public MessageAlertDialog(Activity activity, String title, String messages, Boolean isCancelable,
                              String btnConfirmText, String btnCancelText, OnConfirmListener listener)
    {
        this.activity = activity;
        this.listener = listener;
        this.title = title;
        this.messages = messages;
        this.isCancelable = isCancelable;
        this.btnConfirmText = btnConfirmText;
        this.btnCancelText = btnCancelText;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        tvTitle = dialog.findViewById(R.id.confirm_title);
        tvMessage = dialog.findViewById(R.id.confirm_msessage);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        rlCancel = dialog.findViewById(R.id.rlCancel);

        if (!isCancelable)
        {
            rlCancel.setVisibility(View.GONE);
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