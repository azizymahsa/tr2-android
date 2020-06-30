package com.traap.traapapp.ui.fragments.performanceEvaluation.setEvaluation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.getEvaluationQuestion.GetPlayerEvaluationQuestionResponse;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.setEvaluation.request.QuestionItemRequest;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.adapters.performanceEvaluation.PlayerSetEvaluationAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;


@SuppressLint("ValidFragment")
public class PlayerSetEvaluationFragment extends BaseFragment implements OnAnimationEndListener, PlayerSetEvaluationAdapter.onEValueCheckedChangeListener,
        OnServiceStatus<WebServiceClass<List<GetPlayerEvaluationQuestionResponse>>>, SetPlayerEvaluationImpl.onSetPlayerEvaluationListener
{
    private View rootView;
    private MainActionView mainView;
    private Context context;
    private CircularProgressButton btnConfirm;
    private TextView tvPlayerName;

    private Toolbar mToolbar;
    private ImageView imgProfile;
    private AVLoadingIndicatorView progressImageProfile;
    private RelativeLayout rlPlayerImage;
    private Integer matchId;
    private Integer playerId;
    private String name;
    private String imageURL;

    private RecyclerView recyclerView;
    private PlayerSetEvaluationAdapter adapter;

    private List<QuestionItemRequest> questionRequestList;


    public PlayerSetEvaluationFragment()
    {

    }

    public static PlayerSetEvaluationFragment newInstance(MainActionView mainView, Integer matchId, Integer playerId, String name, String imageURL)
    {
        PlayerSetEvaluationFragment f = new PlayerSetEvaluationFragment();
        Bundle arg = new Bundle();
        arg.putInt("matchId", matchId);
        arg.putInt("playerId", playerId);
        arg.putString("name", name);
        arg.putString("imageURL", imageURL);
        f.setArguments(arg);
        f.setMainView(mainView);
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
            playerId = getArguments().getInt("playerId");
            matchId = getArguments().getInt("matchId");
            name = getArguments().getString("name");
            imageURL = getArguments().getString("imageURL");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_player_set_evaluation, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> getActivity().onBackPressed());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("نتایج ارزیابی عملکرد");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        rootView.findViewById(R.id.flLogoToolbar).setOnClickListener(v -> mainView.backToMainFragment());

        initView();

        return rootView;
    }


    public void initView()
    {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        btnConfirm = rootView.findViewById(R.id.btnConfirm);
        rlPlayerImage = rootView.findViewById(R.id.rlImageProfile);
        NestedScrollView scrollView = rootView.findViewById(R.id.nested);
        progressImageProfile = rootView.findViewById(R.id.progressImageProfile);
        imgProfile = rootView.findViewById(R.id.imgProfile);
        tvPlayerName = rootView.findViewById(R.id.tvPlayerName);

        tvPlayerName.setText(name);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setAdapter();

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) ->
        {
            if (scrollY - oldScrollY > 0)
            {
                Animation animHide = AnimationUtils.loadAnimation(context, R.anim.hide_button);
                rootView.findViewById(R.id.rlImageProfile).startAnimation(animHide);
                rootView.findViewById(R.id.rlImageProfile).setVisibility(View.GONE);
            }
            else
            {
                Animation animShow = AnimationUtils.loadAnimation(context, R.anim.show_button);
                rootView.findViewById(R.id.rlImageProfile).startAnimation(animShow);
                rootView.findViewById(R.id.rlImageProfile).setVisibility(View.VISIBLE);
            }
        });

        btnConfirm.setOnClickListener(v ->
        {
//            btnConfirm.revertAnimation();
            btnConfirm.startAnimation();
            btnConfirm.setClickable(false);

            //send Evaluation List + call API
            SetPlayerEvaluationImpl.SetPlayerEvaluation(matchId, questionRequestList, this);
        });

//        mainView.showLoading();
        progressImageProfile.setVisibility(View.VISIBLE);
        try
        {
            Picasso.with(context).load(imageURL).into(imgProfile, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    progressImageProfile.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    progressImageProfile.setVisibility(View.GONE);
                    Picasso.with(context).load(R.drawable.ic_user_default).into(imgProfile);
                }
            });
        }
        catch (Exception e)
        {
            progressImageProfile.setVisibility(View.GONE);
            Picasso.with(context).load(R.drawable.ic_user_default).into(imgProfile);
        }

        SingletonService.getInstance().getPerformanceEvaluationService().getPlayerEvaluationQuestion(matchId, this);
    }

    @Override
    public void onAnimationEnd()
    {
        btnConfirm.setBackground(ContextCompat.getDrawable(context, R.drawable.background_button_login));
    }

    @Override
    public void onEvaluateSelected(int value, int position)
    {
        questionRequestList.get(position).setScore(value);
    }

    @Override
    public void onReady(WebServiceClass<List<GetPlayerEvaluationQuestionResponse>> response)
    {
        mainView.hideLoading();
        if (response == null || response.data == null)
        {
            showErrorAndBack("خطا در دریافت اطلاعات از سرور!");
            Logger.e("-GetPredictResponse-", "null");
            return;
        }
        if (response.info.statusCode != 200)
        {
            showErrorAndBack(response.info.message);
        }
        else
        {
            adapter = new PlayerSetEvaluationAdapter(context, response.data, this);
            recyclerView.setAdapter(adapter);
            questionRequestList = new ArrayList<>(response.data.size());

            for (int i = 0; i < response.data.size(); i++)
            {
                QuestionItemRequest questionItemRequest = new QuestionItemRequest();
                questionItemRequest.setQuestionId(response.data.get(i).getQuestionId());
                questionItemRequest.setScore(1);
                questionItemRequest.setPlayerId(playerId);
                questionRequestList.add(questionItemRequest);
            }
        }
    }

    @Override
    public void onError(String message)
    {
        mainView.hideLoading();
        Logger.e("-showErrorMessage-", "Error: " + message);

        if (Tools.isNetworkAvailable((Activity) context))
        {
            showErrorAndBack("خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showErrorAndBack(getString(R.string.networkErrorMessage));
        }
    }


    private void showErrorAndBack(String message)
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

    @Override
    public void onSetPlayerEvaluationCompleted(String message)
    {
        btnConfirm.revertAnimation();
        btnConfirm.setClickable(true);

        showAlertSuccess(context, message, "", true);
    }

    @Override
    public void onSetPlayerEvaluationError(String message)
    {
        btnConfirm.revertAnimation();
        btnConfirm.setClickable(true);

        showAlertFailure(context, message, getResources().getString(R.string.error), false);
    }
}
