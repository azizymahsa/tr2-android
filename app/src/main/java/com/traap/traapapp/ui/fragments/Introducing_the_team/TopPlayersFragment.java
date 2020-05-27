package com.traap.traapapp.ui.fragments.Introducing_the_team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.topPlayers.TopPlayerResponse;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.PositionInLeaguesAdapter;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.TopPlayersAdapter;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class TopPlayersFragment extends BaseFragment
{
    private View rootView;
    private RecyclerView rv;
    private NestedScrollView nested;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;

        }
        rootView = inflater.inflate(R.layout.top_players_fragment, container, false);
        getTopPlayers();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView(){
        rv=rootView.findViewById(R.id.rv);
        nested=rootView.findViewById(R.id.nested);
        ViewCompat.setNestedScrollingEnabled(nested,false);
    }

    public void getTopPlayers(){

        SingletonService.getInstance().tractorTeamService().getTech(new OnServiceStatus<WebServiceClass<TopPlayerResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<TopPlayerResponse> response)
            {
                try
                {
                    if (response.info.statusCode==200)
                    {

                        rv.setAdapter(new TopPlayersAdapter(response.data.getResults()));



                    }else{
                        showToast(getActivity(), response.info.message, R.color.red);

                    }
                }
                catch (Exception e){
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");

                }

            }

            @Override
            public void onError(String message)
            {
                try{

                    if (Tools.isNetworkAvailable(getActivity()))
                    {
                        Logger.e("-OnError-", "Error: " + message);
                        showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                    }
                    else
                    {
                        showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                    }
                }catch (Exception e){}
            }
        },"player",true,true);

    }
}


