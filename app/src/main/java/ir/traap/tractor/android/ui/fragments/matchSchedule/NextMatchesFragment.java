package ir.traap.tractor.android.ui.fragments.matchSchedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.league.pastResult.request.RequestPastResult;
import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.traap.tractor.android.apiServices.model.matchSchedule.nextMatches.ResponseNextMatches;
import ir.traap.tractor.android.ui.adapters.Leaguse.DataBean;
import ir.traap.tractor.android.ui.adapters.Leaguse.matchResult.MatchAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.leaguse.pastResult.PastResultFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;

/**
 * Created by MahtabAzizi on 11/16/2019.
 */
public class NextMatchesFragment extends BaseFragment
{
    private MainActionView mainActionView;
    private List<MatchItem> nextMatchesList=new ArrayList<>();
    private View rootView;


    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private RecyclerView recyclerView;
    private MatchAdapter mAdapter;

    public static NextMatchesFragment newInstance(List<MatchItem> nextMatchesList, MainActionView mainActionView)
    {
        NextMatchesFragment fragment = new NextMatchesFragment();
        Bundle args = new Bundle();

        fragment.setMainView(mainActionView);
        fragment.setData(nextMatchesList);
        return fragment;
    }

    private void setData(List<MatchItem> nextMatchesList)
    {
        this.nextMatchesList=nextMatchesList;
    }

    public void initView()
    {
        try
        {
            recyclerView = rootView.findViewById(R.id.rclPast);


        } catch (Exception e)
        {

        }
    }

    private void setMainView(MainActionView mainActionView)
    {
        this.mainActionView = mainActionView;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            rootView = null;
        }

        rootView = inflater.inflate(R.layout.past_match_fragment, container, false);
        initView();
        addDataRecyclerList();

        return rootView;
    }

    private void addDataRecyclerList()
    {

        mAdapter = new MatchAdapter(nextMatchesList,getContext());
        recyclerView.setAdapter(mAdapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MatchAdapter(nextMatchesList, getActivity());
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }

    @Override
    public void onStop()
    {
        super.onStop();


    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    @Override
    public void onPause()
    {
        super.onPause();
    }





}




