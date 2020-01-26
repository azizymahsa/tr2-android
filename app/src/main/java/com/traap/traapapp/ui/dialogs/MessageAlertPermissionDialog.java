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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.traap.traapapp.R;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by MahtabAzizi on 1/21/2020.
 */

@SuppressLint("ValidFragment")

public class MessageAlertPermissionDialog extends DialogFragment implements View.OnClickListener
{
    private MessageAlertDialog.OnConfirmListener listener;

    private Boolean isRightToLeft = false;

    private View rootView;

    private ImageView imageView;

    private Dialog dialog;
    private Activity activity;
    private CircularProgressButton btnConfirm, btnCancel;
    private String messages, title;
    private TextView tvMessage, tvTitle;
    private RelativeLayout rlCancel;
    private Space spaceCancel;
    private Boolean isCancelable;
    private String btnConfirmText = "";
    private String btnCancelText = "";
    public static final int TYPE_ERROR = -1;
    public static final int TYPE_MESSAGE = 0;
    public static final int TYPE_SUCCESS = 1;
    private int type = 0;


    public MessageAlertPermissionDialog() {

    }

    public MessageAlertPermissionDialog(Activity activity, String title, String messages, Boolean isCancelable,
                                        int messageType , MessageAlertDialog.OnConfirmListener listener)
    {
        this.activity = activity;
        this.listener = listener;
        this.title = title;
        this.messages = messages;
        this.isCancelable = isCancelable;
        type = messageType;
    }

    public MessageAlertPermissionDialog(Activity activity, String title, String messages ,int messageType)
    {
        this.activity = activity;
        this.listener = null;
        this.title = title;
        this.messages = messages;
        this.isCancelable = false;
        type = messageType;
    }

    public MessageAlertPermissionDialog(Activity activity, String title, String messages, Boolean isCancelable,
                              String btnConfirmText, String btnCancelText, int messageType , MessageAlertDialog.OnConfirmListener listener)
    {
        this.activity = activity;
        this.listener = listener;
        this.title = title;
        this.messages = messages;
        this.isCancelable = isCancelable;
        this.btnConfirmText = btnConfirmText;
        this.btnCancelText = btnCancelText;
        type = messageType;
    }

    public MessageAlertPermissionDialog(Activity activity, String title, String messages, Boolean isCancelable, String btnConfirmText,
                              String btnCancelText, Boolean isRightToLeft, MessageAlertDialog.OnConfirmListener listener)
    {
        this.activity = activity;
        this.listener = listener;
        this.title = title;
        this.messages = messages;
        this.isCancelable = isCancelable;
        this.btnConfirmText = btnConfirmText;
        this.btnCancelText = btnCancelText;
        this.isRightToLeft = isRightToLeft;
        this.type = TYPE_MESSAGE;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_permission_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        imageView = dialog.findViewById(R.id.imageView);
        tvTitle = dialog.findViewById(R.id.confirm_title);
        tvMessage = dialog.findViewById(R.id.confirm_msessage);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        rlCancel = dialog.findViewById(R.id.rlCancel);
        spaceCancel = dialog.findViewById(R.id.spaceCancel);

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

        switch (type)
        {
            case TYPE_MESSAGE:
            {
                imageView.setVisibility(View.GONE);
                tvMessage.setTextColor(getResources().getColor(R.color.textColorSecondary));
                break;
            }
            case TYPE_ERROR:
            {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.un_check_mark));
                imageView.setVisibility(View.VISIBLE);
                tvMessage.setTextColor(getResources().getColor(R.color.red));
                break;
            }
            case TYPE_SUCCESS:
            {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.check_mark));
                imageView.setVisibility(View.VISIBLE);
                tvMessage.setTextColor(getResources().getColor(R.color.green));
                break;
            }

        }

        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        return dialog;
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
