package com.traap.traapapp.ui.fragments.Introducing_the_team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.traap.traapapp.R;

import androidx.recyclerview.widget.RecyclerView;


public class PlayerSearchAdapter extends RecyclerView.Adapter<PlayerSearchAdapter.ViewHolder>
{
    private Context context;


    public PlayerSearchAdapter()
    {

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.player_search_adapter, parent, false));
    }


    @Override
    public void onBindViewHolder(final PlayerSearchAdapter.ViewHolder holder, final int position)
    {

    }


    @Override
    public int getItemCount()
    {

        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {


        public ViewHolder(View v)
        {
            super(v);


        }
    }


}
