package com.traap.traapapp.ui.fragments.lotteryPrimary.activeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.news.main.News;
import com.traap.traapapp.apiServices.model.points.guide.PointGuide;
import com.traap.traapapp.apiServices.model.points.guide.PointsGuideResponse;
import com.traap.traapapp.models.otherModels.predict.PredictTabModel;
import com.traap.traapapp.ui.activities.points.PointActionView;
import com.traap.traapapp.ui.adapters.lotteryPrimary.LotteryPrimaryActiveAdapter;
import com.traap.traapapp.ui.adapters.points.PointGuidesAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.lotteryPrimary.LotteryPrimaryActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("LotteryPrimaryActiveFragment")
public class LotteryPrimaryActiveFragment extends BaseFragment implements LotteryPrimaryActiveAdapter.OnLotteryItemClickListener
{
    private LotteryPrimaryActionView actionView;
    private Context context;
    private View rootView;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private LotteryPrimaryActiveAdapter adapter;
//    private List<PointGuide> guideList;

    public LotteryPrimaryActiveFragment()
    {

    }

    public static LotteryPrimaryActiveFragment newInstance(LotteryPrimaryActionView actionView)
    {
        LotteryPrimaryActiveFragment f = new LotteryPrimaryActiveFragment();
        f.setActionView(actionView);
        return f;
    }

    private void setActionView(LotteryPrimaryActionView actionView)
    {
        this.actionView = actionView;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_lottery_primary_active, container, false);

        initView();

        return rootView;
    }


    public void initView()
    {
        recyclerView = rootView.findViewById(R.id.recyclerView);

        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        //------------------for test----------------------
        List<PredictTabModel> list = new ArrayList<>();
        for (int i=0; i<2; i++)
        {
            PredictTabModel item = new PredictTabModel();
            item.setId(1);
            item.setTitle("000");
            list.add(item);
        }
        //------------------for test----------------------
        recyclerView.setAdapter(new LotteryPrimaryActiveAdapter(context, list, this));
    }

    @Override
    public void onLotteryItemClick(Integer id)
    {
        actionView.onLotteryPrimaryResultDetails(id);
    }
}
