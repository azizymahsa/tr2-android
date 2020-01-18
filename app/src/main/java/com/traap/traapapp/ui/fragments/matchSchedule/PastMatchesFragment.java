package com.traap.traapapp.ui.fragments.matchSchedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
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
public class PastMatchesFragment extends BaseFragment implements OnAnimationEndListener, View.OnClickListener,MatchAdapter.ItemClickListener
{
    private View rootView;

    private MainActionView mainView;

    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private RecyclerView recyclerView;
    private List<MatchItem> pastMatchesList = new ArrayList<>();
    private MatchAdapter mAdapter;


    public PastMatchesFragment()
    {

    }


    public static PastMatchesFragment newInstance(List<MatchItem> pastMatchesList, MainActionView mainActionView)
    {
        PastMatchesFragment fragment = new PastMatchesFragment();
        Bundle args = new Bundle();

        fragment.setMainView(mainActionView);
        fragment.setData(pastMatchesList);
        return fragment;
    }

    private void setData(List<MatchItem> pastMatchesList)
    {
        this.pastMatchesList = pastMatchesList;
    }


    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

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
        Collections.reverse(pastMatchesList);
        mAdapter = new MatchAdapter(pastMatchesList,getContext(),this);
        recyclerView.setAdapter(mAdapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MatchAdapter(pastMatchesList, getActivity(),this);
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
    public void onAnimationEnd()
    {


    }

    @Override
    public void onClick(View view)
    {
       /* switch (view.getId())
        {
            case R.id.btnConfirm:

                break;


        }*/

    }


    @Override
    public void onItemClick(View view, int position, MatchItem matchItem)
    {

    }

    @Override
    public void onItemPredictClick(View view, int position, MatchItem matchItem)
    {
        PredictFragment pastResultFragment =  PredictFragment.newInstance(mainView, matchItem, matchItem.getIsPredict());

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, pastResultFragment).commit();
    }

    @Override
    public void onItemLogoTeamClick(View view, Integer id, String logo, String title)
    {
        if (id==0){
            showToast(getContext(),"متاسفانه اطلاعاتی برای نمایش وجود ندارد.", 0);
        }else
        {
            mainView.openPastResultFragment(id.toString(), logo, title);
        }

    }
}
