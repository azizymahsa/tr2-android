package com.traap.traapapp.ui.fragments.Introducing_the_team;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.traap.traapapp.R;
import com.traap.traapapp.ui.base.BaseFragment;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import androidx.annotation.Nullable;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class TeamHistoryFragment extends BaseFragment
{
    private View rootView;
    private JustifiedTextView tvHistory;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;

        }
        rootView = inflater.inflate(R.layout.team_history_fragment, container, false);

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
                Log.e("TeamHistoryFragment" ,tvHistory.getHeight()+"" );

            }
        });
    }

    private void initView()
    {
        tvHistory= rootView.findViewById(R.id.tvHistory);
        tvHistory.setText(R.string.history);
    }
}
