package com.traap.traapapp.ui.adapters.Leaguse.pastResult;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getLast5PastMatch.response.Last5PastMatchItem;
import com.traap.traapapp.apiServices.model.league.pastResult.response.Result;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class PastResultAdapter extends RecyclerView.Adapter<PastResultAdapter.ViewHolder>
{
    private final Context mContext;
    private final List<Last5PastMatchItem> mData;
    // private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    public PastResultAdapter(List<Last5PastMatchItem> mData, Context mContext)
    {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.row_past_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

        Last5PastMatchItem item = mData.get(position);

        holder.tvNameAway.setText(item.getAwayTeam().getTeamName());
        holder.tvNameHome.setText(item.getHomeTeam().getTeamName());
        holder.txtCompetition_name.setText(item.getCup().getCupName());

        holder.tvDateLeage.setText(item.getDateStr());

        holder.ftScoreAway.setText(String.valueOf(item.getResult().getAwayScore()));
        holder.ftScoreHome.setText(String.valueOf(item.getResult().getHomeScore()));

        setImageBackground(holder.ivNameAway, item.getAwayTeam().getTeamLogo());
        setImageBackground(holder.ivNameHome, item.getHomeTeam().getTeamLogo());
//        setImageBackground(holder.imgCupLogo, item.getCup().getCupLogo());


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



    // total number of rows
    @Override
    public int getItemCount()
    {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView tvNameAway;
        TextView tvNameHome;

        TextView ftScoreAway;
        TextView ftScoreHome;

        TextView tvDateLeage;
        TextView txtCompetition_name;

        ImageView ivNameAway;
        ImageView ivNameHome;
        LinearLayout llItem;

        ViewHolder(View itemView)
        {
            super(itemView);
            tvNameAway = itemView.findViewById(R.id.tvNameAway);
            tvNameHome = itemView.findViewById(R.id.tvNameHome);
            ivNameAway = itemView.findViewById(R.id.ivNameAway);
            ivNameHome = itemView.findViewById(R.id.ivNameHome);
            txtCompetition_name = itemView.findViewById(R.id.txtCompetition_name);

            ftScoreAway = itemView.findViewById(R.id.ftScoreAway);
            ftScoreHome = itemView.findViewById(R.id.ftScoreHome);

            tvDateLeage = itemView.findViewById(R.id.tvDateLeage);


            llItem = itemView.findViewById(R.id.llItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    /*// convenience method for getting data at click position
    public DataBean getItem(int id)
    {
        return mData.get(id);
    }*/

    // allows clicks events to be caught


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener
    {
        void onItemClick(View view, int position);
    }
}

