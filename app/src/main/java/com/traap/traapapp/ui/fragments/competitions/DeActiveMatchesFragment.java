package com.traap.traapapp.ui.fragments.competitions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getAllCompations.Result;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.enums.MatchScheduleParent;
import com.traap.traapapp.enums.PredictPosition;
import com.traap.traapapp.ui.adapters.compations.CompationsDeActiveAdapter;
import com.traap.traapapp.ui.adapters.leagues.DataBean;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MahsaAzizi on 05/16/2020.
 */
public class DeActiveMatchesFragment extends BaseFragment implements CompationsDeActiveAdapter.ItemClickListener
{
    private MainActionView mainActionView;
    private List<Result> nextMatchesList = new ArrayList<>();
    private View rootView;


    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private RecyclerView recyclerView;
    private CompationsDeActiveAdapter mAdapter;
    private TextView textView;
    private LinearLayout lnrText, lnrlist;

    public static DeActiveMatchesFragment newInstance(List<Result> nextMatchesList, MainActionView mainActionView)
    {
        DeActiveMatchesFragment fragment = new DeActiveMatchesFragment();
        Bundle args = new Bundle();

        fragment.setMainView(mainActionView);
        fragment.setData(nextMatchesList);
        return fragment;
    }

    private void setData(List<Result> nextMatchesList)
    {
        this.nextMatchesList = nextMatchesList;
    }

    public void initView()
    {
        try
        {
            recyclerView = rootView.findViewById(R.id.rclPast);
            textView = rootView.findViewById(R.id.textView);
            lnrText = rootView.findViewById(R.id.lnrText);
            lnrlist = rootView.findViewById(R.id.lnrlist);
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


        //  mAdapter = new CompationsDeActiveAdapter(MatchScheduleParent.NextResultFragment, nextMatchesList, getContext(), this);
        //recyclerView.setAdapter(mAdapter);

        if (nextMatchesList != null)
            if (nextMatchesList.size() > 0)
            {


                lnrText.setVisibility(View.GONE);
                lnrlist.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mAdapter = new CompationsDeActiveAdapter(MatchScheduleParent.NextResultFragment, nextMatchesList, getActivity(), this);
                recyclerView.setAdapter(mAdapter);
            } else
            {
                lnrText.setVisibility(View.VISIBLE);
                lnrlist.setVisibility(View.GONE);
            }
    }


    @Override
    public void onItemClick(View view, int position, Result matchItem)
    {
        mainActionView.onMainQuestionClick(matchItem.getId());

    }

    @Override
    public void onItemPredictClick(View view, int position, Result matchItem)
    {
//        PredictFragment pastResultFragment = PredictFragment.newInstance(mainActionView, matchItem.getId(), matchItem.getIsPredict());
//
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, pastResultFragment).commit();
       // mainActionView.onPredict(PredictPosition.PredictResult, matchItem.getId(), matchItem.getIsActive());
    }

    @Override
    public void onItemLogoTeamClick(View view, Integer id, String logo, String title)
    {
       /* if (id == 0)
        {
            showToast(getActivity(), "متاسفانه اطلاعاتی برای نمایش وجود ندارد.", 0);
        } else
        {
            Prefs.putInt("selectedTab", 2);
            mainActionView.openPastResultFragment(LeagueTableParent.MatchScheduleFragment, "0", false, id.toString(), logo, title);
        }*/
    }
}