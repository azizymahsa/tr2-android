package com.traap.traapapp.ui.fragments.Introducing_the_team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.topPlayers.Result;
import com.traap.traapapp.apiServices.model.topPlayers.TopPlayerResponse;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class CurrentPlayersAdapter extends RecyclerView.Adapter<CurrentPlayersAdapter.ViewHolder>
{
    private Context context;
    private List<Result> results;
    private CurrentPlayerEvent teamEvent;


    public CurrentPlayersAdapter(List<Result> results,CurrentPlayerEvent teamEvent)
    {
        this.results=results;
        this.teamEvent=teamEvent;

    }




    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.current_players_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final CurrentPlayersAdapter.ViewHolder holder, final int position)
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


        holder.tvPlayer.setText(results.get(position).getPersianFirstName()+" "+results.get(position).getPersianLastName());
        holder.tvAttendance.setText(results.get(position).getSeasons()+" فصل");
        holder.tvGoals.setText(results.get(position).getClubGoals()+" گل");
        holder.llRoot.setOnClickListener(v ->
        {
            teamEvent.CurrentPlayerClick(results.get(position));
        });
    }


    @Override
    public int getItemCount()
    {

        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout llRoot;
        private LinearLayout llHeader, llItem;
        private TextView tvRate, tvGoals, tvAttendance, tvPlayer;


        public ViewHolder(View v)
        {
            super(v);
            llRoot=v.findViewById(R.id.llRoot);
            llHeader = v.findViewById(R.id.llHeader);
            llItem = v.findViewById(R.id.llItem);
            tvRate = v.findViewById(R.id.tvRate);
            tvGoals = v.findViewById(R.id.tvGoals);
            tvAttendance = v.findViewById(R.id.tvAttendance);
            tvPlayer = v.findViewById(R.id.tvPlayer);

        }
    }
    public interface CurrentPlayerEvent{
        void CurrentPlayerClick(Result result);
    }

}
