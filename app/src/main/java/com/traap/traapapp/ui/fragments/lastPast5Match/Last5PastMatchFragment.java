package com.traap.traapapp.ui.fragments.lastPast5Match;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getLast5PastMatch.request.Last5PastMatchRequest;
import com.traap.traapapp.apiServices.model.getLast5PastMatch.response.Last5PastMatchItem;
import com.traap.traapapp.apiServices.model.getLast5PastMatch.response.Last5PastMatchResponse;
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.adapters.last5PastMatch.Last5PastMatchAdapter;
import com.traap.traapapp.ui.adapters.lotteryWinnerList.LotteryPredictDetailsAdapter;
import com.traap.traapapp.ui.adapters.lotteryWinnerList.LotteryPredictGeneralAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

@SuppressLint("ValidFragment")
public class Last5PastMatchFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<Last5PastMatchResponse>>, Last5PastMatchAdapter.OnItemClickListener
{
    private View rootView;
    private Context context;
    private MainActionView mainView;
    private List<Last5PastMatchItem> last5PastMatchList;
    private Integer teamLiveScoreId;

    private Toolbar mToolbar;
    private RecyclerView recyclerView;


    public Last5PastMatchFragment()
    {

    }

    public static Last5PastMatchFragment newInstance(MainActionView mainView, Integer teamLiveScoreId)
    {
        Last5PastMatchFragment f = new Last5PastMatchFragment();
        Bundle arg = new Bundle();
        arg.putInt("teamLiveScoreId", teamLiveScoreId);
        f.setMainView(mainView);
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
            teamLiveScoreId = getArguments().getInt("teamLiveScoreId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_last_5_past_match, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> getActivity().onBackPressed());
        mToolbar.findViewById(R.id.flLogoToolbar).setOnClickListener(rootView -> mainView.backToMainFragment());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("آخرین بازی ها");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        initView();

        return rootView;
    }


    public void initView()
    {
        recyclerView = rootView.findViewById(R.id.recyclerView);

        mainView.showLoading();

        Last5PastMatchRequest request = new Last5PastMatchRequest();
        request.setLiveScoreId(String.valueOf(teamLiveScoreId));
        SingletonService.getInstance().getLiveScoreService().getPastResult_v2_Service(request, this);
    }

    @Override
    public void onReady(WebServiceClass<Last5PastMatchResponse> response)
    {
        mainView.hideLoading();
        if (response.data.equals(null))
        {
            showAlertFailure( context, "خطا در دریافت اطلاعات از سرور!", "خطا!", true);
        }
        else if (response.info.statusCode != 200)
        {
            showAlertFailure(context, response.info.message, "خطا!", true);
        }
        else
        {
//            last5PastMatchList = response.data.getLastMatchList();
            Observable.fromIterable(response.data.getLastMatchList())
                    .filter(last5PastMatchItem -> response.data.getLastMatchList().indexOf(last5PastMatchItem) < 5)
                    .toList()
                    .doOnSuccess(last5PastMatchItem ->
                    {
                        last5PastMatchList.addAll(last5PastMatchItem);
                        Logger.e("-last5PastMatchList-", "Size: " + last5PastMatchList.size());
                        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
                        recyclerView.setAdapter(new Last5PastMatchAdapter(context, last5PastMatchList, this));
                    })
                    .subscribe();

//            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
//            recyclerView.setAdapter(new Last5PastMatchAdapter(context, last5PastMatchList, this));
        }
    }

    @Override
    public void onError(String message)
    {
        mainView.hideLoading();
        Logger.e("Last5PastMatchResponse", "onError: " + message);
        showAlertFailure( context, "خطا در دریافت اطلاعات از سرور!", "خطا!", true);
    }

    @Override
    public void onHomeTeamClick(Integer teamId, Integer matchId, Boolean isPredictable)
    {
        mainView.onPredictLeagueTable(teamId, matchId, isPredictable);
    }

    @Override
    public void onAwayTeamClick(Integer teamId, Integer matchId, Boolean isPredictable)
    {
        mainView.onPredictLeagueTable(teamId, matchId, isPredictable);
    }
}
