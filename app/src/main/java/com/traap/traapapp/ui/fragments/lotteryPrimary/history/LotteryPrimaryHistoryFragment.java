package com.traap.traapapp.ui.fragments.lotteryPrimary.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.news.main.News;
import com.traap.traapapp.models.otherModels.predict.PredictTabModel;
import com.traap.traapapp.ui.adapters.lotteryPrimary.LotteryPrimaryActiveAdapter;
import com.traap.traapapp.ui.adapters.lotteryPrimary.LotteryPrimaryHistoryAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.lotteryPrimary.LotteryPrimaryActionView;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("LotteryPrimaryHistoryFragment")
public class LotteryPrimaryHistoryFragment extends BaseFragment implements LotteryPrimaryHistoryAdapter.OnLotteryItemClickListener
{
    private LotteryPrimaryActionView actionView;
    private Context context;
    private View rootView;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private LotteryPrimaryHistoryAdapter adapter;
//    private List<PointGuide> guideList;

    public LotteryPrimaryHistoryFragment()
    {

    }

    public static LotteryPrimaryHistoryFragment newInstance(LotteryPrimaryActionView actionView)
    {
        LotteryPrimaryHistoryFragment f = new LotteryPrimaryHistoryFragment();
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
        rootView = inflater.inflate(R.layout.fragment_lottery_primary_history, container, false);

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
        for (int i=0; i<1; i++)
        {
            PredictTabModel item = new PredictTabModel();
            item.setId(1);
            item.setTitle("000");
            list.add(item);
        }
        //------------------for test----------------------
        recyclerView.setAdapter(new LotteryPrimaryHistoryAdapter(context, list, this));
    }

    @Override
    public void onLotteryItemClick(Integer id)
    {
        actionView.onLotteryPrimaryHistoryWinnerList(id);
    }
}
