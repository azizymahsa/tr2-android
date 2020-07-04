package com.traap.traapapp.ui.adapters.myPredict;

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
import com.traap.traapapp.apiServices.model.predict.predictResult.getMyPredict.MyPredictResults;

import java.util.List;

public class MyPredictAdapter extends RecyclerView.Adapter<MyPredictAdapter.ViewHolder>
{
    private OnItemClickListener mItemClickListener;
    private Context mContext;
    private List<MyPredictResults> list;

    public MyPredictAdapter(Context mContext, List<MyPredictResults> list, OnItemClickListener listener)
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
                R.layout.adapter_my_predict, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        MyPredictResults item = list.get(position);

        try
        {
            holder.tvAway.setText(item.getMatchMyPredict().getAwayTeam().getTeamName());
            holder.tvHome.setText(item.getMatchMyPredict().getHomeTeam().getTeamName());
            holder.tvDate.setText(item.getMatchMyPredict().getMatchDateStr());
            holder.tvLeagueName.setText(item.getMatchMyPredict().getCup().getCupName());
            holder.tvStadiumName.setText(item.getMatchMyPredict().getStadium());

            holder.tvMatchResult.setText(new StringBuilder().append(item.getMatchMyPredict().getResultScore().getAwayScore()).append(" - ").append(item.getMatchMyPredict().getResultScore().getHomeScore()).toString());

            holder.tvPoint.setText(new StringBuilder().append("امتیاز این پیش بینی: ").append(item.getPoint()).append(" تراپ").toString());

            holder.tvPredictResult.setText(new StringBuilder().append(item.getPredictScore().getAwayScore()).append(" - ").append(item.getPredictScore().getHomeScore()).toString());

            setImageBackground(holder.imgAway, item.getMatchMyPredict().getAwayTeam().getTeamLogo());
            setImageBackground(holder.imgHome, item.getMatchMyPredict().getHomeTeam().getTeamLogo());
            setImageBackground(holder.imgCupLogo, item.getMatchMyPredict().getCup().getCupLogo());

            if (item.getIsPredictTrue())
            {
                holder.llYourPredict.setBackgroundColor(mContext.getResources().getColor(R.color.predeictedTrueBackgroundColor));
                holder.tvPredictTitle.setTextColor(mContext.getResources().getColor(R.color.predeictedTrueTextColor));
                holder.tvPredictResult.setTextColor(mContext.getResources().getColor(R.color.predeictedTrueTextColor));
            }
            else
            {
                holder.llYourPredict.setBackgroundColor(mContext.getResources().getColor(R.color.predeictedFalseBackgroundColor));
                holder.tvPredictTitle.setTextColor(mContext.getResources().getColor(R.color.predeictedFalseTextColor));
                holder.tvPredictResult.setTextColor(mContext.getResources().getColor(R.color.predeictedFalseTextColor));
            }

            if (item.getFormationPredict() != null)
            {
                holder.llFormationPredict.setVisibility(View.VISIBLE);

                holder.tvFPredictFormation.setText(item.getFormationPredict().getYourFormationPredict());
                holder.tvTrueFormation.setText(item.getFormationPredict().getTrueFormationResult());

                if (item.getFormationPredict().getPlayerAccuracy() == 0)
                {
                    holder.llPlayerAccuracy.setVisibility(View.GONE);
                    holder.rlShowTrueFormation.setVisibility(View.GONE);
                }
                else
                {
                    holder.tvPlayerAccuracy.setText(new StringBuilder(String.valueOf(item.getFormationPredict().getPlayerAccuracy())).append(" %"));
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
        private RelativeLayout rlAway, rlHome, rlWinnerList, rlShowTrueFormation;
        private LinearLayout llYourPredict, llFormationPredict, llPlayerAccuracy;
        private TextView tvLeagueName, tvStadiumName, tvDate, tvMatchResult, tvAway, tvHome, tvPredictTitle,
                tvPredictResult, tvPoint, tvFPredictFormation, tvTrueFormation, tvPlayerAccuracy;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            imgCupLogo = rootView.findViewById(R.id.imgCupLogo);
            imgAway = rootView.findViewById(R.id.imgAway);
            imgHome = rootView.findViewById(R.id.imgHome);

            rlAway = rootView.findViewById(R.id.rlAway);
            rlHome = rootView.findViewById(R.id.rlHome);
            rlWinnerList = rootView.findViewById(R.id.rlWinnerList);
            rlShowTrueFormation = rootView.findViewById(R.id.rlShowTrueFormation);
            llYourPredict = rootView.findViewById(R.id.llYourPredict);
            llFormationPredict = rootView.findViewById(R.id.llFormationPredict);
            llPlayerAccuracy = rootView.findViewById(R.id.llPlayerAccuracy);

            tvLeagueName = rootView.findViewById(R.id.tvLeagueName);
            tvStadiumName = rootView.findViewById(R.id.tvStadiumName);
            tvDate = rootView.findViewById(R.id.tvDate);
            tvMatchResult = rootView.findViewById(R.id.tvMatchResult);
            tvAway = rootView.findViewById(R.id.tvAway);
            tvHome = rootView.findViewById(R.id.tvHome);
            tvPredictTitle = rootView.findViewById(R.id.tvPredictTitle);
            tvPredictResult = rootView.findViewById(R.id.tvPredictResult);
            tvPoint = rootView.findViewById(R.id.tvPoint);
            tvFPredictFormation = rootView.findViewById(R.id.tvFPredictFormation);
            tvTrueFormation = rootView.findViewById(R.id.tvTrueFormation);
            tvPlayerAccuracy = rootView.findViewById(R.id.tvPlayerAccuracy);

            rlAway.setOnClickListener(this);
            rlHome.setOnClickListener(this);
            rlWinnerList.setOnClickListener(this);
            rlShowTrueFormation.setOnClickListener(this);
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
                        mItemClickListener.onAwayTeamClick(0);
                        break;
                    }
                    case R.id.rlHome:
                    {
                        mItemClickListener.onHomeTeamClick(0);
                        break;
                    }
                    case R.id.rlWinnerList:
                    {
                        mItemClickListener.onShowWinnerList(list.get(getAdapterPosition()).getMatchMyPredict().getId());
                        break;
                    }
                    case R.id.rlShowTrueFormation:
                    {
                        mItemClickListener.onShowTrueFormation(list.get(getAdapterPosition()).getMatchMyPredict().getId());
                        break;
                    }
                }
            }
        }
    }


    public interface OnItemClickListener
    {
        void onShowWinnerList(Integer matchId);

        void onHomeTeamClick(Integer teamId);

        void onAwayTeamClick(Integer teamId);

        void onShowTrueFormation(Integer matchId);
    }
//
//    public void SetOnShowWinnerListListener(final OnItemClickListener mItemClickListener)
//    {
//        this.mItemClickListener = mItemClickListener;
//    }

}
