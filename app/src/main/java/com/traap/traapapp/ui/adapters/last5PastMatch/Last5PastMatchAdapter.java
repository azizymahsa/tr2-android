package com.traap.traapapp.ui.adapters.last5PastMatch;

import android.content.Context;
import android.net.Uri;
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
import com.traap.traapapp.apiServices.model.getLast5PastMatch.response.Last5PastMatchItem;
import com.traap.traapapp.apiServices.model.predict.getMyPredict.MyPredictResults;

import java.util.List;

public class Last5PastMatchAdapter extends RecyclerView.Adapter<Last5PastMatchAdapter.ViewHolder>
{
    private OnItemClickListener mItemClickListener;
    private Context mContext;
    private List<Last5PastMatchItem> list;

    public Last5PastMatchAdapter(Context mContext, List<Last5PastMatchItem> list, OnItemClickListener listener)
    {
        this.mContext = mContext;
        this.list = list;
        mItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_last_five_match, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Last5PastMatchItem item = list.get(position);

        try
        {
            holder.tvAway.setText(item.getAwayTeam().getTeamName());
            holder.tvHome.setText(item.getHomeTeam().getTeamName());
            holder.tvDate.setText(item.getDateStr());
            holder.tvLeagueName.setText(item.getCup().getCupName());
            holder.tvStadiumName.setText(item.getStadium());

            holder.tvMatchResult.setText(item.getResult().getHomeScore() + " - " +
                    item.getResult().getAwayScore());

            setImageBackground(holder.imgAway, item.getAwayTeam().getTeamLogo());
            setImageBackground(holder.imgHome, item.getHomeTeam().getTeamLogo());
            setImageBackground(holder.imgCupLogo, item.getCup().getCupLogo());

            switch (item.getStatus())
            {
                case "1": //win
                {
                    holder.rlHeader.setBackgroundResource(R.drawable.background_past_win_match);
                    break;
                }
                case "-1": //loose
                {
                    holder.rlHeader.setBackgroundResource(R.drawable.background_past_loose_match);
                    break;
                }
                case "0": //equal
                {
                    holder.rlHeader.setBackgroundResource(R.drawable.background_past_equal_match);
                    break;
                }
            }

        }
        catch (Exception e)
        {

        }
    }


    @Override
    public int getItemCount()
    {
        return list.size();
    }

    private void setImageBackground(ImageView imageView, String link)
    {
        try
        {
            Picasso.with(mContext).load(Uri.parse(link)).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                }

                @Override
                public void onError()
                {
                    Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
                }
            });
        }
        catch (NullPointerException e)
        {
            Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        private ImageView imgCupLogo, imgAway, imgHome;
        private RelativeLayout rlAway, rlHome, rlHeader;
        private TextView tvLeagueName, tvStadiumName, tvDate, tvMatchResult, tvAway, tvHome;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            imgCupLogo = rootView.findViewById(R.id.imgCupLogo);
            imgAway = rootView.findViewById(R.id.imgAway);
            imgHome = rootView.findViewById(R.id.imgHome);

            rlAway = rootView.findViewById(R.id.rlAway);
            rlHome = rootView.findViewById(R.id.rlHome);
            rlHeader = rootView.findViewById(R.id.rlHeader);

            tvLeagueName = rootView.findViewById(R.id.tvLeagueName);
            tvStadiumName = rootView.findViewById(R.id.tvStadiumName);
            tvDate = rootView.findViewById(R.id.tvDate);
            tvMatchResult = rootView.findViewById(R.id.tvMatchResult);
            tvAway = rootView.findViewById(R.id.tvAway);
            tvHome = rootView.findViewById(R.id.tvHome);

            rlAway.setOnClickListener(this);
            rlHome.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mItemClickListener != null)
            {
                switch (view.getId())
                {
                    case R.id.rlAway:
                    {
                        mItemClickListener.onAwayTeamClick(
                                list.get(getAdapterPosition()).getAwayTeam().getLiveScoreId(),
                                0,
                                false);
                        break;
                    }
                    case R.id.rlHome:
                    {
                        mItemClickListener.onHomeTeamClick(
                                list.get(getAdapterPosition()).getHomeTeam().getLiveScoreId(),
                                0,
                                false);
                        break;
                    }
                }
            }
        }
    }


    public interface OnItemClickListener
    {
        public void onHomeTeamClick(Integer teamId, Integer matchId, Boolean isPredictable);

        public void onAwayTeamClick(Integer teamId, Integer matchId, Boolean isPredictable);
    }

}
