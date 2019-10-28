package ir.trap.tractor.android.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.ui.fragments.favoriteCard.FavoriteCardActionView;
import library.android.eniac.base.BaseDialog;

/**
 * Created by Javad.Abadi on 7/24/2018.
 */
@SuppressLint("ValidFragment")
public class DialogDeleteCard extends BaseDialog implements View.OnClickListener
{
    private Activity activity;
    private Dialog dialog;
    private TextView tvDescDelete;
    private String desc;
    private CircularProgressButton btnConfirmDelete, btnCancelDelete;
    private FavoriteCardActionView mainView;
    private int position;
    private Integer cardId;


    public DialogDeleteCard(Activity activity, String desc, FavoriteCardActionView mainView, Integer cardId, int position)
    {
        this.activity = activity;
        this.desc = desc;
        this.cardId = cardId;
        this.mainView = mainView;
        this.position = position;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_delete_card);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        tvDescDelete = dialog.findViewById(R.id.tvDescDelete);
        btnConfirmDelete = dialog.findViewById(R.id.btnConfirmDelete);
        btnCancelDelete = dialog.findViewById(R.id.btnCancelDelete);

        btnConfirmDelete.setOnClickListener(this);
        btnCancelDelete.setOnClickListener(this);
        tvDescDelete.setText(desc);


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
            case R.id.btnConfirmDelete:
            {
                mainView.onDeleteCard(cardId, position);
                dismiss();
                break;
            }
            case R.id.btnCancelDelete:
            {
                dismiss();
                break;
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }
}
