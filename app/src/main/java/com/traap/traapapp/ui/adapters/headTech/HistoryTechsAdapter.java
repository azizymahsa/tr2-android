package com.traap.traapapp.ui.adapters.headTech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.techHistory.Result;

import java.util.List;

/**
 * Created by MahtabAzizi on 6/1/2020.
 */
public class HistoryTechsAdapter extends RecyclerView.Adapter<HistoryTechsAdapter.ViewHolder>
{
    private Context context;
    private List<Result> results;


    public HistoryTechsAdapter(List<Result> results)
    {
        this.results=results;

    }


    @Override
    public HistoryTechsAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new HistoryTechsAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_history_tech, parent, false));
    }


    @Override
    public void onBindViewHolder(final HistoryTechsAdapter.ViewHolder holder, final int position)
    {

        if (position%2==0){
            holder.tvTeam.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvRole.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvStartDate.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvEndDate.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvGoals.setTextColor(context.getResources().getColor(R.color.black));

        }else{
            holder.tvTeam.setTextColor(context.getResources().getColor(R.color.warmGray));
            holder.tvRole.setTextColor(context.getResources().getColor(R.color.warmGray));
            holder.tvStartDate.setTextColor(context.getResources().getColor(R.color.warmGray));
            holder.tvEndDate.setTextColor(context.getResources().getColor(R.color.warmGray));
            holder.tvGoals.setTextColor(context.getResources().getColor(R.color.warmGray));

        }

        holder.tvTeam.setText(results.get(position).getTeam());
        holder.tvRole.setText(results.get(position).getRole());
        holder.tvStartDate.setText(results.get(position).getFromDate().toString());
        holder.tvEndDate.setText(results.get(position).getToDate().toString());
        holder.tvGoals.setText(String.valueOf(results.get(position).getClubGoals()+results.get(position).getNationalGoals()));


    }




    @Override
    public int getItemCount()
    {

        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvTeam,tvRole,tvStartDate,tvEndDate,tvGoals;


        public ViewHolder(View v)
        {
            super(v);
            tvTeam=v.findViewById(R.id.tvTeam);
            tvRole=v.findViewById(R.id.tvRole);
            tvStartDate=v.findViewById(R.id.tvStartDate);
            tvEndDate=v.findViewById(R.id.tvEndDate);
            tvGoals=v.findViewById(R.id.tvGoals);

        }
    }


}
