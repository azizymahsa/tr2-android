package com.traap.traapapp.ui.adapters.predict;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.card.Result;
import com.traap.traapapp.apiServices.model.predict.getPredict.response.TeamResults;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.GoToActivity;
import com.traap.traapapp.ui.fragments.favoriteCard.FavoriteCardActionView;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;


/**
 * Created by Javad.Abadi on 7/4/2018.
 */
public class PredictMatchResultAdapter extends RecyclerView.Adapter<PredictMatchResultAdapter.MyViewHolder>
{

    private List<TeamResults> cardList;
    private Context context;
    private Context appContext;

    public PredictMatchResultAdapter(Context context, List<TeamResults> models)
    {
        this.cardList = models;
        this.context = context;
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
        TeamResults item = cardList.get(position);

        try
        {
            holder.tvCupTitle.setText(item.getCup().getName());
            holder.tvMatchResult.setText(item.getAwayTeamScore() + "-" + item.getHomeTeamScore());
            holder.tvHome.setText(item.getHomeTeam().getName());
            holder.tvAway.setText(item.getAwayTeam().getName());
            holder.tvDate.setText(item.getMatchDatetimeStr());

            loadImageIntoIV(item.getHomeTeam().getLogo(), holder.imgHome, holder);
            loadImageIntoIV(item.getAwayTeam().getLogo(), holder.imgAway, holder);

            holder.vAway.setBackgroundColor(Color.parseColor(item.getAwayTeam().getColorCode()));
            holder.vAway2.setBackgroundColor(Color.parseColor(item.getAwayTeam().getColorCode()));

            holder.vHome.setBackgroundColor(Color.parseColor(item.getHomeTeam().getColorCode()));
            holder.vHome2.setBackgroundColor(Color.parseColor(item.getHomeTeam().getColorCode()));
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
        private ImageView imgAway, imgHome;
        private View vAway, vAway2, vHome2, vHome;


        private MyViewHolder(View convertView)
        {
            super(convertView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvCupTitle = itemView.findViewById(R.id.tvCupTitle);
            tvAway = itemView.findViewById(R.id.tvAway);
            tvMatchResult = itemView.findViewById(R.id.tvMatchResult);

            tvHome = itemView.findViewById(R.id.tvHome);
            imgAway = itemView.findViewById(R.id.imgAway);
            imgHome = itemView.findViewById(R.id.imgHome);

            vAway = itemView.findViewById(R.id.vAway);
            vAway2 = itemView.findViewById(R.id.vAway2);
            vHome2 = itemView.findViewById(R.id.vHome2);
            vHome = itemView.findViewById(R.id.vHome);
        }
    }

}
