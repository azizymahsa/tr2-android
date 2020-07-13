package com.traap.traapapp.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
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
 * Created by Javad.Abadi on 6/24/2019.
 */
@SuppressLint("ValidFragment")
public class LotteryMessageAlertDialog extends DialogFragment implements View.OnClickListener
{
    private OnConfirmListener listener;

    private View rootView;

    private ImageView imageView;

    private Dialog dialog;
    private Activity activity;
    private CircularProgressButton btnConfirm;
    private String messages, title;
    private TextView tvMessage, tvTitle, tvScoreMessage, tvChanceMessage;
    private String btnConfirmText = "";
    public static final int TYPE_ERROR = -1;
    public static final int TYPE_SUCCESS = 1;
    private int type = 0;
    private int score;
    private int chance;
    private static boolean shown = false;

    @Override
    public void show(FragmentManager manager, String tag)
    {
        if (shown)
        {
            return;
        }

        super.show(manager, tag);
        shown = true;
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        shown = false;
        super.onDismiss(dialog);
    }

    public LotteryMessageAlertDialog(Activity activity, String title, String messages, String btnConfirmText,
                                     int messageType, int score, int chance, OnConfirmListener listener)
    {
        this.activity = activity;
        this.listener = listener;
        this.title = title;
        this.messages = messages;
        this.btnConfirmText = btnConfirmText;
        this.type = messageType;
        this.score = score;
        this.chance = chance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);

        dialog.setContentView(R.layout.alert_dialog_lottery_message);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        imageView = dialog.findViewById(R.id.imageView);
        tvTitle = dialog.findViewById(R.id.confirm_title);
        tvScoreMessage = dialog.findViewById(R.id.tvScoreMessage);
        tvChanceMessage = dialog.findViewById(R.id.tvChanceMessage);
        tvMessage = dialog.findViewById(R.id.confirm_msessage);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);

        if (title.equalsIgnoreCase(""))
        {
            tvTitle.setVisibility(View.INVISIBLE);
        }
        else
        {
            tvTitle.setText(title);
        }
        tvMessage.setText(messages);

        if (!btnConfirmText.equalsIgnoreCase(""))
        {
            btnConfirm.setText(btnConfirmText);
        }

        tvScoreMessage.setText(String.format("مجموع امتیازات شما: %d", score));
        tvChanceMessage.setText(String.format("تعداد کدهای شانس: %d", chance));

        switch (type)
        {
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
        }
    }

    public interface OnConfirmListener
    {
        void onConfirmClick();
    }


}
