package com.traap.traapapp.ui.fragments.performanceEvaluation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main.PerformanceEvaluationMainResponse;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.adapters.evaluationPlayerItemList.EvaluationPlayerItemListAdapter;
import com.traap.traapapp.ui.adapters.evaluationPlayerItemList.EvaluationPlayerRowListAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.dialogs.PlayerEvaluationDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;


@SuppressLint("ValidFragment")
public class PerformanceEvaluationFragment extends BaseFragment implements EvaluationPlayerItemListAdapter.OnPlayerItemClick,
        OnServiceStatus<WebServiceClass<PerformanceEvaluationMainResponse>>, PerformanceEvaluationActionView
{
    private View rootView;
    private MainActionView mainView;
    private TextView tvAwayHeader, tvHomeHeader, tvMatchDate, tvCurrentMatchResult;
    private ImageView imgHomeHeader, imgAwayHeader, imgCupLogo, imgBackground;
    private int rcSystemTeamWidth = 0;
    private int rcSystemTeamHeight = 0;

    private Integer matchId;
    private MatchItem matchItem;
    private Context context;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvHeaderPopularNo;

    private EvaluationPlayerRowListAdapter adapter;
    private RecyclerView rcSystemTeam;

    public PerformanceEvaluationFragment()
    {
        // 7 row with GK
        // any row 5 column
    }

    public static PerformanceEvaluationFragment newInstance(MainActionView mainView, Integer matchId, MatchItem matchItem)
    {
        PerformanceEvaluationFragment f = new PerformanceEvaluationFragment();
        f.setMainView(mainView);

        Bundle arg = new Bundle();
        arg.putInt("matchId", matchId);
        arg.putParcelable("matchItem", matchItem);

        f.setArguments(arg);

        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }


    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            matchId = getArguments().getInt("matchId");
//            matchItem = getArguments().getParcelable("matchItem");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_performance_evaluation, container, false);

        initView();

        return rootView;
    }


    public void initView()
    {
        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> getActivity().onBackPressed());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("ارزیابی عملکرد بازیکنان");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

        rootView.findViewById(R.id.rlShirt).setOnClickListener(v ->

                startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100)
        );

        mToolbar.findViewById(R.id.flLogoToolbar).setOnClickListener(v -> mainView.backToMainFragment());

        tvMatchDate = rootView.findViewById(R.id.tvMatchDate);
        tvCurrentMatchResult = rootView.findViewById(R.id.tvCurrentMatchResult);

        tvAwayHeader = rootView.findViewById(R.id.tvAwayHeader);
        tvHomeHeader = rootView.findViewById(R.id.tvHomeHeader);

        imgAwayHeader = rootView.findViewById(R.id.imgAwayHeader);
        imgHomeHeader = rootView.findViewById(R.id.imgHomeHeader);

        imgBackground = rootView.findViewById(R.id.imgBackground);

        imgCupLogo = rootView.findViewById(R.id.imgCupLogo);
        rcSystemTeam = rootView.findViewById(R.id.rcSystemTeam);

        //-------------------------------get dimension------------------------------------
        ViewTreeObserver vto = rcSystemTeam.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                rcSystemTeam.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                rcSystemTeamWidth = rcSystemTeam.getMeasuredWidth();
                rcSystemTeamHeight = rcSystemTeam.getMeasuredHeight();

                Log.e("--dimension--", "width: " + rcSystemTeamWidth + ", height: " + rcSystemTeamHeight);
            }
        });
        //-------------------------------get dimension------------------------------------

        rcSystemTeam.setLayoutManager(new LinearLayoutManager(context));

        mainView.showLoading();
        SingletonService.getInstance().getPerformanceEvaluationService().getMainEvaluation(matchId, this);
    }


    @Override
    public void onPlayerClick(int matchId, int positionId)
    {
        PlayerEvaluationDialog dialog = new PlayerEvaluationDialog((Activity) context, matchId, positionId, this);
        dialog.show(((Activity) context).getFragmentManager(), "playerEvaluationDialog");
    }


    @Override
    public void onReady(WebServiceClass<PerformanceEvaluationMainResponse> response)
    {
        mainView.hideLoading();
        if (response == null || response.data == null)
        {
            showErrorAndBackToMain("خطا در دریافت اطلاعات از سرور!");
            Logger.e("-GetPredictResponse-", "null");
            return;
        }
        if (response.info.statusCode != 200)
        {
            showErrorAndBackToMain(response.info.message);
        }
        else
        {
            tvMatchDate.setText(response.data.getMatch().getMatchDatetimeStr());
            tvAwayHeader.setText(response.data.getMatch().getAwayTeam().getTeamName());
            tvHomeHeader.setText(response.data.getMatch().getHomeTeam().getTeamName());

            tvCurrentMatchResult.setText(
                    new StringBuilder()
                            .append(response.data.getMatch().getLastMatchResult().getAwayScore())
                            .append(" - ")
                            .append(response.data.getMatch().getLastMatchResult().getHomeScore())
            );

            setImageIntoIV(imgAwayHeader, response.data.getMatch().getAwayTeam().getTeamLogo());
            setImageIntoIV(imgHomeHeader, response.data.getMatch().getHomeTeam().getTeamLogo());
            setImageIntoIV(imgCupLogo, response.data.getMatch().getCup().getCupLogo());

            adapter = new EvaluationPlayerRowListAdapter(context, response.data.getMatch().getMatchId(), response.data.getRowList(), rcSystemTeamWidth / 5, this);
            rcSystemTeam.setAdapter(adapter);
            rcSystemTeam.setNestedScrollingEnabled(false);

            Picasso.with(context).load(response.data.getBackgroundImage()).into(imgBackground);

            adapter.GetAllItemHeight(heightItems ->
            {
                int rcHeight = 0;
                for (int height: heightItems)
                {
                    rcHeight += height;
                }

                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, rcHeight);
                Logger.e("--rcSystemTeam height--", "height: " + rcHeight);
                rcSystemTeam.setLayoutParams(params);
            });
        }
    }

    @Override
    public void onError(String message)
    {
        mainView.hideLoading();
        Logger.e("-showErrorMessage-", "Error: " + message);

        if (Tools.isNetworkAvailable((Activity) context))
        {
            showErrorAndBackToMain("خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showErrorAndBackToMain(getString(R.string.networkErrorMessage));
        }
    }

    private void showErrorAndBackToMain(String message)
    {
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, getResources().getString(R.string.error),
                message, false, MessageAlertDialog.TYPE_ERROR, new MessageAlertDialog.OnConfirmListener()
        {
            @Override
            public void onConfirmClick()
            {
                getActivity().onBackPressed();
            }

            @Override
            public void onCancelClick()
            {

            }
        });
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }

    private void setImageIntoIV(ImageView imageView, String link)
    {
        try
        {
            Picasso.with(context).load(link).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {

                }

                @Override
                public void onError()
                {
                    Picasso.with(context).load(R.drawable.img_failure).into(imageView);
                }
            });
        }
        catch (Exception e)
        {
            Logger.e("-Load Image-", "" + e.getMessage());
            e.printStackTrace();
            Picasso.with(context).load(R.drawable.img_failure).into(imageView);
        }
    }

    @Override
    public void showErrorAlert(String message)
    {
        showAlertFailure(context, message, "خطا!", false);
    }

    @Override
    public void onPlayerShowEvaluatedResult(int matchId, int positionId, String name, String imageURL)
    {
//        showDebugToast((Activity) context, "onPlayerShowEvaluatedResult");
        mainView.onPlayerPerformanceEvaluationResult(matchId, positionId, name, imageURL);
    }

    @Override
    public void onPlayerSetEvaluation(int matchId, int positionId, String name, String imageURL)
    {
//        showDebugToast((Activity) context, "onPlayerSetEvaluation");
        mainView.onSetPlayerPerformanceEvaluation(matchId, positionId, name, imageURL);
    }
}
