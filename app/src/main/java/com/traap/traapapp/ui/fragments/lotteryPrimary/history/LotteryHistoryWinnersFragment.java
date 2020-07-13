package com.traap.traapapp.ui.fragments.lotteryPrimary.history;

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
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.adapters.evaluationPlayerItemList.EvaluationPlayerItemListAdapter;
import com.traap.traapapp.ui.adapters.evaluationPlayerItemList.EvaluationPlayerRowListAdapter;
import com.traap.traapapp.ui.adapters.lotteryPrimary.LotteryHistoryWinnersAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.dialogs.PlayerEvaluationDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.performanceEvaluation.PerformanceEvaluationActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class LotteryHistoryWinnersFragment extends BaseFragment
{
    private View rootView;
    private TextView tvResultMessage;

    private Integer lotteryId;
    private Context context;
    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvHeaderPopularNo;

    private RecyclerView recyclerView;
    private LotteryHistoryWinnersAdapter adapter;

    public LotteryHistoryWinnersFragment()
    { }

    public static LotteryHistoryWinnersFragment newInstance(MainActionView mainView, Integer lotteryId)
    {
        LotteryHistoryWinnersFragment f = new LotteryHistoryWinnersFragment();
        f.setMainView(mainView);

        Bundle arg = new Bundle();
        arg.putInt("lotteryId", lotteryId);

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
            lotteryId = getArguments().getInt("lotteryId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_lottery_hist_winners, container, false);

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
        tvTitle.setText("لیست برندگان");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

        rootView.findViewById(R.id.rlShirt).setOnClickListener(v ->

                startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100)
        );

        mToolbar.findViewById(R.id.flLogoToolbar).setOnClickListener(v -> mainView.backToMainFragment());
        tvResultMessage = rootView.findViewById(R.id.tvResultMessage);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        tvResultMessage.setText("متأسفانه شما در این قرعه کشی برنده نشده اید.");

        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        //----------------------for test---------------------------
        List<Winner> list = new ArrayList<>();
        for (int i=0; i<5; i++)
        {
            Winner winner = new Winner();
            winner.setFirstName("انیاک");
            winner.setLastName("انیاکی");
            winner.setMobile("0912***1234");
            winner.setDescription("دو میلیون تومان");
        }
        //----------------------for test---------------------------
        recyclerView.setAdapter(new LotteryHistoryWinnersAdapter(context, list));

//        mainView.showLoading();
//        SingletonService.getInstance().getPerformanceEvaluationService().getMainEvaluation(matchId, this);
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
}
