package com.traap.traapapp.ui.fragments.performanceEvaluation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.adapters.playerItemList.PlayerItemListAdapter;
import com.traap.traapapp.ui.adapters.playerItemList.PlayerRowListAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.PlayerEvaluationDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;


@SuppressLint("ValidFragment")
public class PerformanceEvaluationFragment extends BaseFragment implements PlayerItemListAdapter.OnPlayerItemClick
{
    private View rootView;
    private MainActionView mainView;
    private TextView tvAwayHeader, tvHomeHeader, tvMatchDate;
    private ImageView imgHomeHeader, imgAwayHeader, imgCupLogo;

    private Integer matchId;
    private MatchItem matchItem;
    private Context context;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvHeaderPopularNo;

    private PlayerRowListAdapter adapter;
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
            matchItem = getArguments().getParcelable("matchItem");
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
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("ارزیابی عملکرد بازیکنان");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

        rootView.findViewById(R.id.rlShirt).setOnClickListener(v ->

                startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100)
        );

        mToolbar.findViewById(R.id.flLogoToolbar).setOnClickListener(v -> mainView.backToMainFragment());

        tvMatchDate = rootView.findViewById(R.id.tvMatchDate);

        tvAwayHeader = rootView.findViewById(R.id.tvAwayHeader);
        tvHomeHeader = rootView.findViewById(R.id.tvHomeHeader);

        imgAwayHeader = rootView.findViewById(R.id.imgAwayHeader);
        imgHomeHeader = rootView.findViewById(R.id.imgHomeHeader);

        imgCupLogo = rootView.findViewById(R.id.imgCupLogo);
        rcSystemTeam = rootView.findViewById(R.id.rcSystemTeam);

        rcSystemTeam.setLayoutManager(new LinearLayoutManager(context));
        adapter = new PlayerRowListAdapter(context, this);
        rcSystemTeam.setAdapter(adapter);
    }


    @Override
    public void onPlayerClick()
    {
        PlayerEvaluationDialog dialog = new PlayerEvaluationDialog(getActivity());
        dialog.show(((Activity) context).getFragmentManager(), "playerEvaluationDialog");
    }
}
