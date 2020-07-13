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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.traap.traapapp.R;
import com.traap.traapapp.utilities.Logger;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by Javad.Abadi on 6/24/2019.
 */
@SuppressLint("ValidFragment")
public class LotteryGetScoreDialog extends DialogFragment
{
    private OnConfirmListener listener;

    private ImageView imageView;

    private Dialog dialog;
    private RangeSeekBar seekBar;
    private Activity activity;
    private CircularProgressButton btnConfirm;
    private TextView tvTitle, tvScore;
    private int minScore;
    private int maxScore;
    private int yourScore;
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

    public LotteryGetScoreDialog(Activity activity, int minScore, int maxScore, int yourScore, OnConfirmListener listener)
    {
        this.activity = activity;
        this.listener = listener;
        this.yourScore = yourScore;
        this.maxScore = maxScore;
        this.minScore = minScore;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);

        dialog.setContentView(R.layout.alert_dialog_lottery_get_score);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        imageView = dialog.findViewById(R.id.imageView);
        tvTitle = dialog.findViewById(R.id.confirm_title);
        tvScore = dialog.findViewById(R.id.tvScore);
        seekBar = dialog.findViewById(R.id.seekBar);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);

        tvTitle.setText("تعداد امتیاز مورد نظر جهت تبدیل به کد شانس را معین نمایید.");
        tvScore.setText("مجموع امتیازات شما: " + yourScore);

        seekBar.setIndicatorTextDecimalFormat("0");
        seekBar.setSteps(maxScore - maxScore - 1);
        seekBar.setRange(minScore, maxScore, 1);
        seekBar.setProgress(1f);

        btnConfirm.setOnClickListener(v ->
        {
            if (listener != null)
            {
                Logger.e("-leftValue progress-", "" + seekBar.getLeftSeekBar().getProgress());
                listener.onConfirmClick(Math.round(seekBar.getLeftSeekBar().getProgress()));
            }
            dismiss();
        });

        return dialog;
    }

    public interface OnConfirmListener
    {
        void onConfirmClick(int score);
    }


}
