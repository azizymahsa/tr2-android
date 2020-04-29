package com.traap.traapapp.ui.fragments.Introducing_the_team.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.traap.traapapp.R;

import androidx.core.view.ViewCompat;
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

        if (position%2==0){
            holder.tvRate.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvYear.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvLeague.setTextColor(context.getResources().getColor(R.color.black));
        }else{
            holder.tvRate.setTextColor(context.getResources().getColor(R.color.warmGray));
            holder.tvYear.setTextColor(context.getResources().getColor(R.color.warmGray));
            holder.tvLeague.setTextColor(context.getResources().getColor(R.color.warmGray));
        }


    }




    @Override
    public int getItemCount()
    {

        return 10;
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
