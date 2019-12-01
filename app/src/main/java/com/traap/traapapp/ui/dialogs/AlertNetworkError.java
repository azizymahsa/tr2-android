package com.traap.traapapp.ui.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;

public class AlertNetworkError implements View.OnClickListener {
    android.app.AlertDialog dialog;
    TextView tvTitle;
    View dialogView;
    LayoutInflater inflater;
    android.app.AlertDialog.Builder builder;
    Context activity;
    TextView btnOk, btnCancel;
    Prefs prefs;
    boolean isFinish;

    public AlertNetworkError(final Context activity, boolean warning, boolean isFinish) {
        this.activity = activity;
        this.isFinish = isFinish;
        builder = new android.app.AlertDialog.Builder(activity);
        inflater = LayoutInflater.from(activity);
        dialogView = inflater.inflate(R.layout.alert_dialog_error, null);
        builder.setView(dialogView);
        btnOk = dialogView.findViewById(R.id.btnOk);
        btnCancel=dialogView.findViewById(R.id.btnCancel);
        tvTitle = dialogView.findViewById(R.id.tvTitle);
        //tvDesciption=dialogView.findViewById(R.id.tvDescriptionExit);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        try {
            dialog.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                deleteSharedprefs();
                dialog.cancel();
                //activity.finish();
                break;
            case R.id.btnCancel:
                dialog.dismiss();
                break;
        }
    }

    private void deleteSharedprefs() {
        prefs.remove("isLogin");



    }
}