package com.traap.traapapp.ui.fragments.Introducing_the_team;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.traap.traapapp.R;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.PositionInLeaguesAdapter;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class PositionInLeaguesFragment  extends BaseFragment
{
    private View rootView;
    private RecyclerView rv;
    private NestedScrollView nested;
    public static Integer height;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;

        }
        rootView = inflater.inflate(R.layout.in_leagues_fragment, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                height=rv.getHeight();
            }
        });
    }

    private void initView(){
        rv=rootView.findViewById(R.id.rv);
        nested=rootView.findViewById(R.id.nested);
        rv.setAdapter(new PositionInLeaguesAdapter());
        ViewCompat.setNestedScrollingEnabled(nested,false);
    }

}


