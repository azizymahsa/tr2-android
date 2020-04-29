package com.traap.traapapp.ui.fragments.Introducing_the_team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.traap.traapapp.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by MahsaAzizi on 11/23/2019.
 */
public class TopPlayersAdapter extends RecyclerView.Adapter<TopPlayersAdapter.ViewHolder>
{
    private Context context;


    public TopPlayersAdapter()
    {

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.top_players_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final TopPlayersAdapter.ViewHolder holder, final int position)
    {

        if (position % 2 == 0)
        {
            holder.tvRate.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvGoals.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvAttendance.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvPlayer.setTextColor(context.getResources().getColor(R.color.black));
        } else
        {
            holder.tvRate.setTextColor(context.getResources().getColor(R.color.warmGray));
            holder.tvGoals.setTextColor(context.getResources().getColor(R.color.warmGray));
            holder.tvAttendance.setTextColor(context.getResources().getColor(R.color.warmGray));
            holder.tvPlayer.setTextColor(context.getResources().getColor(R.color.warmGray));
        }

        holder.tvRate.setText(String.valueOf(position + 1));
    }


    @Override
    public int getItemCount()
    {

        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout llHeader, llItem;
        private TextView tvRate, tvGoals, tvAttendance, tvPlayer;


        public ViewHolder(View v)
        {
            super(v);
            llHeader = v.findViewById(R.id.llHeader);
            llItem = v.findViewById(R.id.llItem);
            tvRate = v.findViewById(R.id.tvRate);
            tvGoals = v.findViewById(R.id.tvGoals);
            tvAttendance = v.findViewById(R.id.tvAttendance);
            tvPlayer = v.findViewById(R.id.tvPlayer);

        }
    }


}
