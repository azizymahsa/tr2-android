package com.traap.traapapp.ui.adapters.predict.predictResult;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response.BarChart;
import com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response.TeamDetails;

import java.util.List;

public class PredictBarChartProgressAdapter extends RecyclerView.Adapter<PredictBarChartProgressAdapter.ViewHolder>
{
    private Context mContext;
    private List<BarChart> list;
    private TeamDetails homeTeam, awayTeam;

    public PredictBarChartProgressAdapter(Context mContext, List<BarChart> list, TeamDetails homeTeam, TeamDetails awayTeam)
    {
        this.mContext = mContext;
        this.list = list;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_predict_bar_chart, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        BarChart item = list.get(position);

        holder.tvChartPredict.setText(item.getAwayScore() + " - " + item.getHomeScore());
        holder.tvChartTotalUser.setText(item.getTotalUser() + "%");

        int height = (int) mContext.getResources().getDimension(R.dimen.PredictBarChartHeight);

        LinearLayout.LayoutParams paramsSpace = new LinearLayout.LayoutParams(0, height);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, height);
        params.weight = (float) (item.getTotalUser() + 20);
        paramsSpace.weight = (float) (100 - item.getTotalUser());
        holder.progressChart.setLayoutParams(params);
        holder.space.setLayoutParams(paramsSpace);

        holder.vColorAway.setBackgroundColor(Color.parseColor(awayTeam.getTeamColorCode()));
        holder.vColorHome.setBackgroundColor(Color.parseColor(homeTeam.getTeamColorCode()));

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvChartPredict, tvChartTotalUser;
        private RelativeLayout progressChart;
        private Space space;
        private View vColorAway, vColorHome;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            progressChart = rootView.findViewById(R.id.progressChart);
            tvChartPredict = rootView.findViewById(R.id.tvChartPredict);
            tvChartTotalUser = rootView.findViewById(R.id.tvChartTotalUser);
            space = rootView.findViewById(R.id.space);
            vColorAway = rootView.findViewById(R.id.vColorAway);
            vColorHome = rootView.findViewById(R.id.vColorHome);
        }
    }

}
