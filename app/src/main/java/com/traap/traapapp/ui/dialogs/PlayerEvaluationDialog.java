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
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.playerSubstitution.request.PlayerSubstitutionRequest;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.playerSubstitution.response.PerformanceEvaluationPlayerSubstitutionResponse;
import com.traap.traapapp.ui.adapters.performanceEvaluation.PerformanceEvaluationPlayerAdapter;
import com.traap.traapapp.ui.fragments.performanceEvaluation.PerformanceEvaluationActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import java.util.List;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;


@SuppressLint("ValidFragment")
public class PlayerEvaluationDialog extends DialogFragment implements PerformanceEvaluationPlayerAdapter.OnPlayerItemClick,
        OnServiceStatus<WebServiceClass<List<PerformanceEvaluationPlayerSubstitutionResponse>>>
{
    private Context context;

    private View rootView;
    private RecyclerView recyclerView;
    private PerformanceEvaluationPlayerAdapter adapter;
    private RelativeLayout rlProgress;

    private int matchId, positionId;

    private LinearLayoutManager layoutManager;

    private Dialog dialog;
    private Activity activity;
    private PerformanceEvaluationActionView actionView;


    public PlayerEvaluationDialog(Activity activity, int matchId, int positionId, PerformanceEvaluationActionView actionView)
    {
        this.activity = activity;
        this.matchId = matchId;
        this.positionId = positionId;
        this.actionView = actionView;
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

        rlProgress = dialog.findViewById(R.id.rlProgress);
        recyclerView = dialog.findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
//        adapter = new PerformanceEvaluationPlayerAdapter(this);
//        recyclerView.setAdapter(adapter);

        SingletonService.getInstance().getPerformanceEvaluationService().getEvaluationSubstitution(matchId, new PlayerSubstitutionRequest(positionId), this);

        return dialog;
    }


    @Override
    public void onReady(WebServiceClass<List<PerformanceEvaluationPlayerSubstitutionResponse>> response)
    {
        rlProgress.setVisibility(View.GONE);
        if (response == null || response.data == null)
        {
            dismiss();
            actionView.showErrorAlert("خطا در دریافت اطلاعات از سرور!");
            Logger.e("-getEvaluationSubstitution-", "null");
            return;
        }
        if (response.info.statusCode != 200)
        {
            dismiss();
            actionView.showErrorAlert(response.info.message);
        }
        else
        {
            adapter = new PerformanceEvaluationPlayerAdapter(this, context, matchId, response.data);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onError(String message)
    {
        rlProgress.setVisibility(View.GONE);
        dismiss();
        if (Tools.isNetworkAvailable((Activity) context))
        {
            actionView.showErrorAlert(message);
        }
        else
        {
            actionView.showErrorAlert(getString(R.string.networkErrorMessage));
        }
    }

    @Override
    public void onPlayerShowEvaluatedResult(int matchId, int playerId, String name, String imageURL)
    {
        actionView.onPlayerShowEvaluatedResult(matchId, playerId, name, imageURL);
        dismiss();
    }

    @Override
    public void onPlayerSetEvaluation(int matchId, int playerId, String name, String imageURL)
    {
        actionView.onPlayerSetEvaluation(matchId, playerId, name, imageURL);
        dismiss();
    }
}
