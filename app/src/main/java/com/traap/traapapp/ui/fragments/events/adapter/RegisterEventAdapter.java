package com.traap.traapapp.ui.fragments.events.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.utilities.Utility;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class RegisterEventAdapter extends RecyclerView.Adapter<RegisterEventAdapter.ViewHolder>
{


    private Activity activity;
    private Integer count;

    public RegisterEventAdapter(Activity activity, Integer count)
    {

        this.activity = activity;
        this.count = count;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.register_event_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {


        holder.tvCount.setText("ﻣﺸﺨﺼﺎت ﺷﺮﮐﺖ ﮐﻨﻨﺪه "+ Utility.getNameNumber(position)+" :");
        if (position==count-1){
            holder.vUnderLine.setVisibility(View.GONE);
        }else{
            holder.vUnderLine.setVisibility(View.VISIBLE);

        }

    }


    @Override
    public int getItemCount()
    {

        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvCount;
        private View vUnderLine;



        public ViewHolder(View v)
        {
            super(v);
            tvCount=v.findViewById(R.id.tvCount);
            vUnderLine=v.findViewById(R.id.vUnderLine);
        }
    }



}
