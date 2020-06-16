package com.traap.traapapp.ui.fragments.events.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.traap.traapapp.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class DetailEventAdapter extends RecyclerView.Adapter<DetailEventAdapter.ViewHolder>
{


    private Activity activity;
    private ArrayList<String> count;
    private DetailEventAdapterEvents events;

    public DetailEventAdapter(Activity activity, DetailEventAdapterEvents events, ArrayList<String> count)
    {

        this.activity=activity;
        this.events=events;
        this.count=count;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_event_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, R.layout.my_spinner_item, count);
        holder.spCount.setAdapter(dataAdapter);
        holder.spCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int spPosition, long id)
            {
                events.onItemCountSelected(count.get(spPosition),position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }


    @Override
    public int getItemCount()
    {
        return 2;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private Spinner spCount;


        public ViewHolder(View v)
        {
            super(v);
            spCount=v.findViewById(R.id.spCount);

        }
    }
    public interface DetailEventAdapterEvents{

        public void onItemCountSelected(String count,Integer position);
    }


}
