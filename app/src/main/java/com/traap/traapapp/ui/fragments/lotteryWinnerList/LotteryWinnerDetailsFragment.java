package com.traap.traapapp.ui.fragments.lotteryWinnerList;

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
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.adapters.lotteryWinnerList.LotteryPredictDetailsAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class LotteryWinnerDetailsFragment extends BaseFragment
{
    private View rootView;
    private Context context;
    private MainActionView mainView;
    private List<Winner> winnerList;

    private Toolbar mToolbar;
    private NestedScrollView scrollView;
    private RecyclerView recyclerView;
    private CardView cardPoints;
//    private Integer matchId;
    private TextView tvWinnerCount;


    public LotteryWinnerDetailsFragment()
    {

    }

    public static LotteryWinnerDetailsFragment newInstance(MainActionView mainView, List<Winner> winnerList)
    {
        LotteryWinnerDetailsFragment f = new LotteryWinnerDetailsFragment();
        Bundle arg = new Bundle();
        arg.putParcelableArrayList("winnerList", (ArrayList<? extends Parcelable>) winnerList);
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
            winnerList = getArguments().getParcelableArrayList("winnerList");
//            matchId = getArguments().getInt("matchId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_lottery_winner_details, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> getActivity().onBackPressed());
        mToolbar.findViewById(R.id.flLogoToolbar).setOnClickListener(rootView -> mainView.backToMainFragment());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("لیست برندگان");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        initView();

        return rootView;
    }


    public void initView()
    {
        scrollView = rootView.findViewById(R.id.nested);
        cardPoints = rootView.findViewById(R.id.cardPoints);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        tvWinnerCount = rootView.findViewById(R.id.tvWinnerCount);

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) ->
        {
            if (scrollY - oldScrollY > 0)
            {
                Animation animHide = AnimationUtils.loadAnimation(context, R.anim.hide_button);
                cardPoints.startAnimation(animHide);
                cardPoints.setVisibility(View.GONE);
            }
            else
            {
                Animation animShow = AnimationUtils.loadAnimation(context, R.anim.show_button);
                cardPoints.startAnimation(animShow);
                cardPoints.setVisibility(View.VISIBLE);
            }
        });

        tvWinnerCount.setText(String.valueOf(winnerList.size()));

        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        recyclerView.setAdapter(new LotteryPredictDetailsAdapter(context, winnerList));

//        mainView.showLoading();
//        SingletonService.getInstance().getLotteryWinnerListService().getLotteryWinnerListService(matchId, this);
    }

//    @Override
//    public void onReady(WebServiceClass<GetLotteryWinnerListResponse> response)
//    {
//        mainView.hideLoading();
//        if (response.data.equals(null))
//        {
//            showAlertFailure( context, "خطا در دریافت اطلاعات از سرور!", "خطا!", true);
//        }
//        else if (response.info.statusCode != 200)
//        {
//            showAlertFailure(context, response.info.message, "خطا!", true);
//        }
//        else
//        {
//            winnerList = response.data.getWinnerList();
//            tvWinnerCount.setText(String.valueOf(winnerList.size()));
//
//            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
//            recyclerView.setAdapter(new LotteryPredictDetailsAdapter(context, winnerList));
//        }
//    }
//
//    @Override
//    public void onError(String message)
//    {
//        mainView.hideLoading();
//        Logger.e("GetLotteryWinnerListResponse", "onError: " + message);
//        showAlertFailure( context, "خطا در دریافت اطلاعات از سرور!", "خطا!", true);
//    }
}
