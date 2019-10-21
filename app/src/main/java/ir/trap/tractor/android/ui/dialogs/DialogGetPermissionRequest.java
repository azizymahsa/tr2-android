package ir.trap.tractor.android.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ir.trap.tractor.android.R;
import library.android.eniac.base.BaseDialog;

/**
 * Created by Javad.Abadi on 7/31/2018.
 */
@SuppressLint("ValidFragment")
public class DialogGetPermissionRequest extends BaseDialog implements View.OnClickListener
{
    private Dialog dialog;
    private CircularProgressButton btnConfirm, btnCancel;
    private Activity activity;
//    private DialogDeleteSessionEvent sessionEvent;
    private OnButtonClick onButtonClick;

    public DialogGetPermissionRequest(Activity activity, OnButtonClick onButtonClick)
    {
        this.activity = activity;
        this.onButtonClick = onButtonClick;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_get_permission);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

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
        switch (v.getId())
        {
            case R.id.btnConfirm:
                dismiss();

                this.onButtonClick.onPositiveButton();

                break;
            case R.id.btnCancel:
                this.onButtonClick.onNegativeButton();
                break;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    public interface OnButtonClick
    {
        void onPositiveButton();

        void onNegativeButton();
    }
}
