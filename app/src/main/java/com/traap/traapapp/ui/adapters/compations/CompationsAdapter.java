package com.traap.traapapp.ui.adapters.compations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.enums.MatchScheduleParent;

import java.util.ArrayList;
import java.util.List;

public class CompationsAdapter extends RecyclerView.Adapter<CompationsAdapter.ViewHolder>
{
    private Context mContext;
    private LayoutInflater mInflater;
    private MatchScheduleParent parent;

    private List<MatchItem> pastMatchesList = new ArrayList<>();
    private View view;
    private ItemClickListener mClickListener;

    public CompationsAdapter(MatchScheduleParent parent, List<MatchItem> pastMatchesList, Context context, ItemClickListener mClickListener)
    {
        this.pastMatchesList = pastMatchesList;
        this.mContext = context;
        this.parent = parent;
        this.mInflater = LayoutInflater.from(mContext);
        this.mClickListener = mClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        view = mInflater.inflate(R.layout.list_row_compations, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

        MatchItem item = pastMatchesList.get(position);
        String[] week = item.getDescription().split("/");
        holder.tvLeageName.setText(item.getCup().getName() + " (" + week[0] + ") ");
        holder.tvStadiumname.setText("ورزشگاه " + item.getStadium().getName());
        holder.tvDate.setText(item.getMatchDatetimeStr());

        // holder.rlHost.setOnClickListener(mClickListener.onItemLogoTeamClick(););


        try
        {
            Picasso.with(mContext).load(item.getTeamHome().getLogo()).into(holder.imgHost, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    // holder.progress.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    // holder.progressBar.setVisibility(View.GONE);
                    Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.imgHost);
                }
            });
        }
        catch (Exception e1)
        {
            //  holder.progressBar.setVisibility(View.GONE);
            Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.imgHost);
        }
        holder.tvHome.setText(item.getTeamHome().getName());
        //-----------------------------------------------------------------------------------------------
        try
        {
            Picasso.with(mContext).load(item.getTeamAway().getLogo()).into(holder.imgGuest, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    // holder.progress.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    // holder.progressBar.setVisibility(View.GONE);
                    Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.imgGuest);
                }
            });
        }
        catch (Exception e1)
        {
            //  holder.progressBar.setVisibility(View.GONE);
            Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.imgGuest);
        }
        holder.tvAway.setText(item.getTeamAway().getName());
        //-----------------------------------------------------------------------------------------------
        try
        {
            if (item.getResult() != null)
            {

                String result[] = item.getResult().split("-");
                holder.tvMatchResult.setText(Integer.parseInt(result[1]) + "  -  " + Integer.parseInt(result[0]));

                holder.tvMatchResult.setVisibility(View.VISIBLE);
                holder.imgCenter.setVisibility(View.GONE);
            }
            else
            {
                holder.tvMatchResult.setVisibility(View.GONE);
                holder.imgCenter.setVisibility(View.VISIBLE);
            }
           /* String s = item.getFtScore();//2-0
            String[] i = s.split("-");
            holder.tvMatchResult.setText(i[1]);//2
            holder.tvMatchResult.setText(i[0]);//0*/


            //-----------------------------------------------------------------------------------------------

            holder.progress.setVisibility(View.GONE);

            if (item.getBuyEnable() && item.getIsPredict())
            {
                holder.tvBuyTicket.setVisibility(View.VISIBLE);
                getPredictVisibility(true, holder);

                holder.lnrBuyEnable.setVisibility(View.VISIBLE);
            }
            else if (item.getIsPredict())
            {
                holder.lnrBuyEnable.setVisibility(View.VISIBLE);
                getPredictVisibility(true, holder);
                holder.tvBuyTicket.setVisibility(View.GONE);

            }
            else if (item.getBuyEnable())
            {
                holder.tvBuyTicket.setVisibility(View.VISIBLE);
                getPredictVisibility(false, holder);

                holder.lnrBuyEnable.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.lnrBuyEnable.setVisibility(View.GONE);
                getPredictVisibility(false, holder);
                holder.tvBuyTicket.setVisibility(View.GONE);

            }
            if (item.getIs_chart_predict())
            {
                //  holder.tvBuyTicket.setVisibility(View.GONE);
                getPredictVisibility(true, holder);

                holder.lnrBuyEnable.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    private void getPredictVisibility(boolean visibility, ViewHolder holder)
    {
        if (parent == MatchScheduleParent.PastResultFragment)
        {
            holder.tvPredictResult.setVisibility(View.GONE);
        }
        else
        {
            if (visibility)
            {
                holder.tvPredictResult.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.tvPredictResult.setVisibility(View.GONE);
            }
        }
    }

    // total number of rows
    @Override
    public int getItemCount()
    {
        return pastMatchesList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        View rlHost, rlGuest;
        TextView tvLeageName;
        TextView tvStadiumname;
        TextView tvDate;

        TextView tvPredictResult;
        TextView tvBuyTicket;

        TextView tvHome;
        TextView tvAway;

        TextView tvMatchResult;
        ProgressBar progress;

        ImageView imgHost;
        ImageView imgGuest;
        ImageView imgCenter;
        LinearLayout lnrBuyEnable;

        ViewHolder(View itemView)
        {
            super(itemView);
            tvLeageName = itemView.findViewById(R.id.tvLeageName);
            tvStadiumname = itemView.findViewById(R.id.tvStadiumname);

            tvPredictResult = itemView.findViewById(R.id.tvPredictResult);
            tvBuyTicket = itemView.findViewById(R.id.tvBuyTicket);

            tvDate = itemView.findViewById(R.id.tvDate);

            imgHost = itemView.findViewById(R.id.imgHost);
            tvHome = itemView.findViewById(R.id.tvHome);
            imgGuest = itemView.findViewById(R.id.imgGuest);
            tvAway = itemView.findViewById(R.id.tvAway);
            rlHost = itemView.findViewById(R.id.rlHost);
            rlGuest = itemView.findViewById(R.id.rlGuest);

            imgCenter = itemView.findViewById(R.id.imgCenter);
            tvMatchResult = itemView.findViewById(R.id.tvMatchResult);
            progress = itemView.findViewById(R.id.progress);
            lnrBuyEnable = itemView.findViewById(R.id.lnrBuyEnable);

            rlGuest.setOnClickListener(this);
            rlHost.setOnClickListener(this);
            tvBuyTicket.setOnClickListener(this);
            tvPredictResult.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.tvBuyTicket:
                    if (mClickListener != null)
                    {
                        mClickListener.onItemClick(view, getAdapterPosition(), pastMatchesList.get(getAdapterPosition()));
                    }
                    break;
                case R.id.tvPredictResult:
                    if (mClickListener != null)
                    {
                        mClickListener.onItemPredictClick(view, getAdapterPosition(), pastMatchesList.get(getAdapterPosition()));
                    }
                    break;

                case R.id.rlHost:
                    if (mClickListener != null)
                    {
                        mClickListener.onItemLogoTeamClick(view,
                                pastMatchesList.get(getAdapterPosition()).getTeamHome().getLivescoreId(),
                                pastMatchesList.get(getAdapterPosition()).getTeamHome().getLogo(),
                                pastMatchesList.get(getAdapterPosition()).getTeamHome().getName()

                        );
                    }
                    break;
                case R.id.rlGuest:
                    if (mClickListener != null)
                    {
                        mClickListener.onItemLogoTeamClick(view,
                                pastMatchesList.get(getAdapterPosition()).getTeamAway().getLivescoreId(),
                                pastMatchesList.get(getAdapterPosition()).getTeamAway().getLogo(),
                                pastMatchesList.get(getAdapterPosition()).getTeamAway().getName()

                        );
                    }
                    break;
            }
        }
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener
    {
        void onItemClick(View view, int position, MatchItem matchItem);

        void onItemPredictClick(View view, int position, MatchItem matchItem);

        void onItemLogoTeamClick(View view, Integer id, String logo, String name);
    }
}


