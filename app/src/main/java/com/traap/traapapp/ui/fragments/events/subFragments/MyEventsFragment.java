package com.traap.traapapp.ui.fragments.events.subFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traap.traapapp.R;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.about.AboutFragment;
import com.traap.traapapp.ui.fragments.events.EventDetailFragment;
import com.traap.traapapp.ui.fragments.events.EventsFragment;
import com.traap.traapapp.ui.fragments.events.MyDetailEventFragment;
import com.traap.traapapp.ui.fragments.events.adapter.MyEventAdapter;
import com.traap.traapapp.ui.fragments.events.adapter.NextEventAdapter;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class MyEventsFragment extends BaseFragment implements View.OnClickListener, MyEventAdapter.MyEventAdapterEvent
{

    private View view;
    private MainActionView mainView;
    private RecyclerView rvEvents;

    public static MyEventsFragment newInstance(MainActionView mainView)
    {
        MyEventsFragment f = new MyEventsFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public MyEventsFragment()
    {
    }


    public static AboutFragment newInstance()
    {
        AboutFragment fragment = new AboutFragment();


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_my_events, container, false);
        initViews();
        return view;
    }

    private void initViews()
    {
        rvEvents=view.findViewById(R.id.rvEvents);
        rvEvents.setAdapter(new MyEventAdapter(getActivity(),this));
    }


    @Override
    public void onClick(View v)
    {

    }

    @Override
    public void onClickMyEventAdapter()
    {
        ((MainActivity)getActivity()).setFragment(MyDetailEventFragment.newInstance(mainView));
        ((MainActivity)getActivity()).replaceFragment(((MainActivity)getActivity()).getFragment(), "MyDetailEventFragment");

    }
}
