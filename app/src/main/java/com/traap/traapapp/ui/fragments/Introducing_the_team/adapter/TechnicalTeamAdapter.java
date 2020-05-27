package com.traap.traapapp.ui.fragments.Introducing_the_team.adapter;

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


public class TechnicalTeamAdapter extends RecyclerView.Adapter<TechnicalTeamAdapter.ViewHolder>
{
    private Context context;
    private List<Result> results;
    private TechnicalTeamEvent teamEvent;


    public TechnicalTeamAdapter(List<Result> results,TechnicalTeamEvent teamEvent)
    {
        this.results=results;
        this.teamEvent=teamEvent;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.technical_team_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final TechnicalTeamAdapter.ViewHolder holder, final int position)
    {

        if (position%2==0){
            holder.tvRole.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.black));
        }else{
            holder.tvRole.setTextColor(context.getResources().getColor(R.color.warmGray));
            holder.tvName.setTextColor(context.getResources().getColor(R.color.warmGray));
        }
        holder.tvName.setText(results.get(position).getPersianFirstName()+" "+results.get(position).getPersianLastName());
        holder.tvRole.setText(results.get(position).getRole());
        Glide.with(context).load(results.get(position).getNationality()).into(holder.ivFlag);
        holder.llRoot.setOnClickListener(v ->
        {
            teamEvent.TechnicalTeamClick(results.get(position));
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
        private TextView tvRole,tvYear,tvName;
        private ImageView ivFlag;


        public ViewHolder(View v)
        {
            super(v);
            llRoot=v.findViewById(R.id.llRoot);
            tvRole=v.findViewById(R.id.tvRole);
            ivFlag=v.findViewById(R.id.ivFlag);
            tvName=v.findViewById(R.id.tvName);

        }
    }
    public interface TechnicalTeamEvent{
         void TechnicalTeamClick(Result result);
    }


}
