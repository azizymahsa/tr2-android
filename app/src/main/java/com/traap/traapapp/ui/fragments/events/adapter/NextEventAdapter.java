package com.traap.traapapp.ui.fragments.events.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.makeramen.roundedimageview.RoundedImageView;
import com.traap.traapapp.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class NextEventAdapter extends RecyclerView.Adapter<NextEventAdapter.ViewHolder>
{


    private Activity activity;
    private NextEventAdapterEvent event;

    public NextEventAdapter(Activity activity, NextEventAdapterEvent event)
    {

        this.activity = activity;
        this.event = event;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.next_event_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        if (position % 2 == 0)
        {
            holder.imgBackground.setImageDrawable(activity.getResources().getDrawable(R.drawable.test_event_1));
        } else
        {
            holder.imgBackground.setImageDrawable(activity.getResources().getDrawable(R.drawable.test_event_2));

        }
        holder.llRoot.setOnClickListener(v ->
        {
            event.onNextEventClick();

        });


    }


    @Override
    public int getItemCount()
    {

        return 4;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private RoundedImageView imgBackground;
        private LinearLayout llRoot;


        public ViewHolder(View v)
        {
            super(v);
            imgBackground = v.findViewById(R.id.imgBackground);
            llRoot = v.findViewById(R.id.llRoot);

        }
    }

    public interface NextEventAdapterEvent
    {


        public void onNextEventClick();
    }


}
