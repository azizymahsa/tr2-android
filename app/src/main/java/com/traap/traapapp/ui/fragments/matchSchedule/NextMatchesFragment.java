package com.traap.traapapp.ui.fragments.matchSchedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.ui.adapters.Leaguse.DataBean;
import com.traap.traapapp.ui.adapters.Leaguse.matchResult.MatchAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.predict.PredictFragment;
import com.traap.traapapp.utilities.Tools;

/**
 * Created by MahtabAzizi on 11/16/2019.
 */
public class NextMatchesFragment extends BaseFragment implements MatchAdapter.ItemClickListener
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

        mAdapter = new MatchAdapter(nextMatchesList,getContext(),this);
        recyclerView.setAdapter(mAdapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MatchAdapter(nextMatchesList, getActivity(),this);
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


    @Override
    public void onItemClick(View view, int position, MatchItem matchItem)
    {
        mainActionView.getBuyEnable(() ->
        {
           /* btnBuyTicket.revertAnimation();
            btnBuyTicket.setClickable(true);*/
        });
        /*BuyTicketsFragment pastResultFragment =  BuyTicketsFragment.newInstance(mainActionView, matchItem);;

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, pastResultFragment).commit();*/
    }

    @Override
    public void onItemPredictClick(View view, int position, MatchItem matchItem)
    {
        PredictFragment pastResultFragment =  PredictFragment.newInstance(mainActionView, matchItem, matchItem.getIsPredict());

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, pastResultFragment).commit();
    }

    @Override
    public void onItemLogoTeamClick(View view, Integer id, String logo, String title)
    {
        if (id==0){
            showToast(getActivity(),"متاسفانه اطلاعاتی برای نمایش وجود ندارد.", 0);
        }else
        {
            mainActionView.openPastResultFragment(id.toString(), logo, title);
        }
    }
}




