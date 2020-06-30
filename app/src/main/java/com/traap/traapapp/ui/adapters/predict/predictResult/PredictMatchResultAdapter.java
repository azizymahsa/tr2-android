package com.traap.traapapp.ui.adapters.predict.predictResult;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response.MatchTeamResults;
import com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response.TeamDetails;
import com.traap.traapapp.singleton.SingletonContext;

import java.util.List;


/**
 * Created by Javad.Abadi on 7/4/2018.
 */
public class PredictMatchResultAdapter extends RecyclerView.Adapter<PredictMatchResultAdapter.MyViewHolder>
{
    private OnLogoClickListener listener;
    private List<MatchTeamResults> cardList;
    private TeamDetails homeTeam, awayTeam;
    private Context appContext;

    public PredictMatchResultAdapter(List<MatchTeamResults> models, TeamDetails homeTeam, TeamDetails awayTeam, OnLogoClickListener listener)
    {
        this.cardList = models;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.listener = listener;
        appContext = SingletonContext.getInstance().getContext();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_predict_team_result, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        MatchTeamResults item = cardList.get(position);

        try
        {
            holder.tvCupTitle.setText(item.getCup().getCupName());
            holder.tvMatchResult.setText(item.getTeamResultScore().getAwayTeamScore() + " - " + item.getTeamResultScore().getHomeTeamScore());
            holder.tvHome.setText(homeTeam.getTeamName());
            holder.tvAway.setText(awayTeam.getTeamName());
            holder.tvDate.setText(item.getMatchDatetimeStr());

            loadImageIntoIV(homeTeam.getTeamLogo(), holder.imgHome, holder);
            loadImageIntoIV(awayTeam.getTeamLogo(), holder.imgAway, holder);
            loadImageIntoIV(item.getCup().getCupLogo(), holder.imgCupLogo, holder);
//
//            holder.imgHome.setOnClickListener(v -> listener.onHomeLogoClick());
//            holder.imgAway.setOnClickListener(v -> listener.onAwayLogoClick());

            holder.vAway.setBackgroundColor(Color.parseColor(awayTeam.getTeamColorCode()));
            holder.vAway2.setBackgroundColor(Color.parseColor(awayTeam.getTeamColorCode()));

            holder.vHome.setBackgroundColor(Color.parseColor(homeTeam.getTeamColorCode()));
            holder.vHome2.setBackgroundColor(Color.parseColor(homeTeam.getTeamColorCode()));
        }
        catch (Exception e)
        {

        }

    }

    private void loadImageIntoIV(String link, ImageView imageView, @NonNull MyViewHolder holder)
    {
        Picasso.with(appContext).load(link).into(imageView, new Callback()
        {
            @Override
            public void onSuccess()
            { }

            @Override
            public void onError()
            {
                Picasso.with(appContext).load(R.drawable.img_failure).into(imageView);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return cardList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvDate, tvCupTitle, tvAway, tvMatchResult, tvHome;
        private ImageView imgAway, imgHome, imgCupLogo;
        private View vAway, vAway2, vHome2, vHome;

        private MyViewHolder(View convertView)
        {
            super(convertView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvCupTitle = itemView.findViewById(R.id.tvCupTitle);
            tvAway = itemView.findViewById(R.id.tvAway);
            tvHome = itemView.findViewById(R.id.tvHome);
            tvMatchResult = itemView.findViewById(R.id.tvMatchResult);

            imgAway = itemView.findViewById(R.id.imgAway);
            imgHome = itemView.findViewById(R.id.imgHome);
            imgCupLogo = itemView.findViewById(R.id.imgCupLogo);

            vAway = itemView.findViewById(R.id.vAway);
            vAway2 = itemView.findViewById(R.id.vAway2);
            vHome2 = itemView.findViewById(R.id.vHome2);
            vHome = itemView.findViewById(R.id.vHome);

            imgHome.setOnClickListener(v -> listener.onHomeLogoClick());
            imgAway.setOnClickListener(v -> listener.onAwayLogoClick());
        }
    }

    public interface OnLogoClickListener
    {
        void onHomeLogoClick();

        void onAwayLogoClick();
    }

}
