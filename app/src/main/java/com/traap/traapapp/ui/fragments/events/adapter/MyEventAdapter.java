package com.traap.traapapp.ui.fragments.events.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.traap.traapapp.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.ViewHolder>
{


    private Activity activity;
    private MyEventAdapterEvent event;

    public MyEventAdapter(Activity activity, MyEventAdapterEvent event)
    {

        this.activity = activity;
        this.event = event;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_event_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        holder.llRoot.setOnClickListener(v ->
        {

            event.onClickMyEventAdapter();
        });


    }


    @Override
    public int getItemCount()
    {
        return 4;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout llRoot;


        public ViewHolder(View v)
        {
            super(v);
            llRoot = v.findViewById(R.id.llRoot);
        }
    }

    public interface MyEventAdapterEvent
    {

        void onClickMyEventAdapter();
    }
}
