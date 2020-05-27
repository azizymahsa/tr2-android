package com.traap.traapapp.ui.fragments.Introducing_the_team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.cup.Datum;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class PositionInLeaguesAdapter extends RecyclerView.Adapter<PositionInLeaguesAdapter.ViewHolder>
{
    private Context context;
    private List<Datum> results;


    public PositionInLeaguesAdapter(List<Datum> results)
    {
        this.results=results;

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

        if (position%2==0){
            holder.tvRate.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvYear.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvLeague.setTextColor(context.getResources().getColor(R.color.black));
        }else{
            holder.tvRate.setTextColor(context.getResources().getColor(R.color.warmGray));
            holder.tvYear.setTextColor(context.getResources().getColor(R.color.warmGray));
            holder.tvLeague.setTextColor(context.getResources().getColor(R.color.warmGray));
        }

        holder.tvLeague.setText(results.get(position).getCup());
        holder.tvYear.setText(results.get(position).getYear()+"");


    }




    @Override
    public int getItemCount()
    {

        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout llHeader,llItem;
        private TextView tvRate,tvYear,tvLeague;


        public ViewHolder(View v)
        {
            super(v);
            llHeader=v.findViewById(R.id.llHeader);
            llItem=v.findViewById(R.id.llItem);
            tvRate=v.findViewById(R.id.tvRate);
            tvYear=v.findViewById(R.id.tvYear);
            tvLeague=v.findViewById(R.id.tvLeague);

        }
    }


}
