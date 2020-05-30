package com.traap.traapapp.ui.fragments.Introducing_the_team.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.topPlayers.Result;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class PlayerSearchAdapter extends RecyclerView.Adapter<PlayerSearchAdapter.ViewHolder>
{
    private Context context;
    private List<Result> results;
    private Activity activity;
    private PlayerSearchAdapterEvent event;


    public PlayerSearchAdapter(List<Result> results, Activity activity,PlayerSearchAdapterEvent event)
    {
        this.results=results;
        this.event=event;

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

        holder.tvName.setText(results.get(position).getRole()+" - "+results.get(position).getPersianFirstName()+" "+results.get(position).getPersianLastName());
        //Glide.with(activity).load(results.get(position).).into(ivTeamLogo);
        holder.llRoot.setOnClickListener(v ->
        {
            event.onPlayerSearchClick(results.get(position));
        });

    }


    @Override
    public int getItemCount()
    {

        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvName;
        private ImageView ivPhoto;
        private LinearLayout llRoot;


        public ViewHolder(View v)
        {
            super(v);
            tvName=v.findViewById(R.id.tvName);
            ivPhoto=v.findViewById(R.id.ivPhoto);
            llRoot=v.findViewById(R.id.llRoot);


        }
    }
    public interface PlayerSearchAdapterEvent{
        void onPlayerSearchClick(Result result);

}


}
