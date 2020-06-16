package com.traap.traapapp.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.traap.traapapp.R;
import com.traap.traapapp.ui.adapters.performanceEvaluation.PerformanceEvaluationPlayerAdapter;

import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;


@SuppressLint("ValidFragment")
public class PlayerEvaluationDialog extends DialogFragment implements PerformanceEvaluationPlayerAdapter.OnPlayerItemClick
{
    private Context context;

    private View rootView;
    private RecyclerView recyclerView;
    private PerformanceEvaluationPlayerAdapter adapter;

    private LinearLayoutManager layoutManager;

    private Dialog dialog;
    private Activity activity;


    public PlayerEvaluationDialog(Activity activity)
    {
        this.activity = activity;
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
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_player_evaluation);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        recyclerView = dialog.findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PerformanceEvaluationPlayerAdapter(this);
        recyclerView.setAdapter(adapter);

        return dialog;
    }


    @Override
    public void onPlayerClick()
    {
        dismiss();
//        startActivity();
    }

}
