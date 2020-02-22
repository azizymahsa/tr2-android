package com.traap.traapapp.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by Javad.Abadi on 6/24/2019.
 */
@SuppressLint("ValidFragment")
public class PredictWinListDialog extends DialogFragment
{
    private View rootView;
    private Context context;

    private Dialog dialog;
    private Integer matchId;

    private ImageView imageView;
    private AVLoadingIndicatorView progress;
    private CircularProgressButton btnBack;

    private RecyclerView recyclerView;
    //    private Adapter
    private LinearLayoutManager layoutManager;

//    @Override
//    public void show(FragmentManager manager, String tag)
//    {
//        if (shown)
//        {
//            return;
//        }
//
//        super.show(manager, tag);
//        shown = true;
//    }

    public PredictWinListDialog(Integer matchId)
    {
        this.matchId = matchId;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        dialog = new Dialog(context, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.dialog_predict_win_list);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        imageView = dialog.findViewById(R.id.image);
        progress = dialog.findViewById(R.id.progress);
        btnBack = dialog.findViewById(R.id.btnBack);
        recyclerView = dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        progress.show();

        getData();

        btnBack.setOnClickListener(v ->
        {
            dismiss();
        });

        return dialog;
    }

    private void getData()
    {
        //getData
        //hide progress
        //show image
        //show List with adapter
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
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);
    }


}
