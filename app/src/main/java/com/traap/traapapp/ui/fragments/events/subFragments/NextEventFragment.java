package com.traap.traapapp.ui.fragments.events.subFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traap.traapapp.R;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.about.AboutFragment;
import com.traap.traapapp.ui.fragments.competitions.CompationsFragment;
import com.traap.traapapp.ui.fragments.events.EventDetailFragment;
import com.traap.traapapp.ui.fragments.events.EventsFragment;
import com.traap.traapapp.ui.fragments.events.adapter.NextEventAdapter;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class NextEventFragment extends BaseFragment implements View.OnClickListener, NextEventAdapter.NextEventAdapterEvent
{
    private View view;
    private MainActionView mainView;
    private RecyclerView rvEvents;



    public static NextEventFragment newInstance(MainActionView mainView)
    {
        NextEventFragment f = new NextEventFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public NextEventFragment()
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
        view = inflater.inflate(R.layout.fragment_next_event, container, false);
        initViews();
        return view;
    }

    private void initViews()
    {
        rvEvents=view.findViewById(R.id.rvEvents);
        rvEvents.setAdapter(new NextEventAdapter(getActivity(),this));

    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    public void onNextEventClick()
    {

        ((MainActivity)getActivity()).setFragment(EventDetailFragment.newInstance(mainView));
        ((MainActivity)getActivity()).replaceFragment(((MainActivity)getActivity()).getFragment(), "EventDetailFragment");

    }
}
