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


    public TechnicalTeamAdapter(List<Result> results)
    {
        this.results=results;

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
    }




    @Override
    public int getItemCount()
    {

        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout llHeader,llItem;
        private TextView tvRole,tvYear,tvName;
        private ImageView ivFlag;


        public ViewHolder(View v)
        {
            super(v);
            llHeader=v.findViewById(R.id.llHeader);
            llItem=v.findViewById(R.id.llItem);
            tvRole=v.findViewById(R.id.tvRole);
            ivFlag=v.findViewById(R.id.ivFlag);
            tvName=v.findViewById(R.id.tvName);

        }
    }


}
