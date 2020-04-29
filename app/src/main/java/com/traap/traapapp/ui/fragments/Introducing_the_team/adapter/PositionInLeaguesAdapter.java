package com.traap.traapapp.ui.fragments.Introducing_the_team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.traap.traapapp.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by MahsaAzizi on 11/23/2019.
 */
public class PositionInLeaguesAdapter extends RecyclerView.Adapter<PositionInLeaguesAdapter.ViewHolder>
{
    private Context context;


    public PositionInLeaguesAdapter()
    {

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.position_in_leagues_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final PositionInLeaguesAdapter.ViewHolder holder, final int position)
    {


    }




    @Override
    public int getItemCount()
    {

        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout llHeader,llItem;


        public ViewHolder(View v)
        {
            super(v);
            llHeader=v.findViewById(R.id.llHeader);
            llItem=v.findViewById(R.id.llItem);

        }
    }


}
