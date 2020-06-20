package com.traap.traapapp.ui.fragments.performanceEvaluation.setEvaluation;

import android.annotation.SuppressLint;
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

import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.adapters.performanceEvaluation.PlayerSetEvaluationAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.wang.avi.AVLoadingIndicatorView;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;


@SuppressLint("ValidFragment")
public class PlayerSetEvaluationFragment extends BaseFragment implements OnAnimationEndListener, PlayerSetEvaluationAdapter.onEValueCheckedChangeListener
{
    private View rootView;
    private MainActionView mainView;
    private Context context;
    private CircularProgressButton btnConfirm;

    private Toolbar mToolbar;
    private ImageView imgProfile;
    private AVLoadingIndicatorView progressImageProfile;
    private RelativeLayout rlPlayerImage;
    private Integer matchId;
    private Integer playerId;

    private RecyclerView recyclerView;
    private PlayerSetEvaluationAdapter adapter;


    public PlayerSetEvaluationFragment()
    {

    }

    public static PlayerSetEvaluationFragment newInstance(MainActionView mainView, Integer matchId, Integer playerId)
    {
        PlayerSetEvaluationFragment f = new PlayerSetEvaluationFragment();
        Bundle arg = new Bundle();
        arg.putInt("matchId", matchId);
        arg.putInt("playerId", playerId);
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_player_set_evaluation, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
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
            btnConfirm.revertAnimation();
            btnConfirm.startAnimation();

            //send Evaluation List + call API
        });
    }

    @Override
    public void onAnimationEnd()
    {
        btnConfirm.setBackground(ContextCompat.getDrawable(context, R.drawable.background_button_login));
    }

    @Override
    public void onEValueSelected(int value, int position)
    {

    }
}
