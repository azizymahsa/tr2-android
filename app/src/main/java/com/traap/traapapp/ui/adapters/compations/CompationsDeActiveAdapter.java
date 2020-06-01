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
import com.traap.traapapp.apiServices.model.getAllCompations.Result;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.enums.MatchScheduleParent;

import java.util.ArrayList;
import java.util.List;

public class CompationsDeActiveAdapter extends RecyclerView.Adapter<CompationsDeActiveAdapter.ViewHolder>
{
    private Context mContext;
    private LayoutInflater mInflater;
    private MatchScheduleParent parent;

    private List<Result> pastMatchesList = new ArrayList<>();
    private View view;
    private ItemClickListener mClickListener;

    public CompationsDeActiveAdapter(MatchScheduleParent parent, List<Result> pastMatchesList, Context context, ItemClickListener mClickListener)
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
        view = mInflater.inflate(R.layout.list_row_deactive, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

       Result item = pastMatchesList.get(position);


        holder.txtTitle.setText(item.getTitle());


       try
        {
            Picasso.with(mContext).load(item.getImage()).into(holder.lnrBaseBack, new Callback()
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
                    Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.lnrBaseBack);
                }
            });
        }
        catch (Exception e1)
        {
            //  holder.progressBar.setVisibility(View.GONE);
            Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.lnrBaseBack);
        }

    }


    // total number of rows
    @Override
    public int getItemCount(){

       return pastMatchesList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        View rlHost, rlGuest;
        TextView txtTitle;

        ProgressBar progress;


        ImageView lnrBaseBack;

        ViewHolder(View itemView)
        {
            super(itemView);
            lnrBaseBack = itemView.findViewById(R.id.lnrBaseBack);

            txtTitle = itemView.findViewById(R.id.txtTitle);

            rlHost = itemView.findViewById(R.id.rlHost);
            rlGuest = itemView.findViewById(R.id.rlGuest);


            progress = itemView.findViewById(R.id.progress);

            rlGuest.setOnClickListener(this);
            rlHost.setOnClickListener(this);
            lnrBaseBack.setOnClickListener(this);

        }


        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.lnrBaseBack:
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
                                pastMatchesList.get(getAdapterPosition()).getId(),
                                pastMatchesList.get(getAdapterPosition()).getImage(),
                                pastMatchesList.get(getAdapterPosition()).getTitle()

                        );
                    }
                    break;
                case R.id.rlGuest:
                    if (mClickListener != null)
                    {
                        mClickListener.onItemLogoTeamClick(view,
                                pastMatchesList.get(getAdapterPosition()).getId(),
                                pastMatchesList.get(getAdapterPosition()).getImage(),
                                pastMatchesList.get(getAdapterPosition()).getTitle()

                        );
                    }
                    break;
            }
        }
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener
    {
        void onItemClick(View view, int position, Result matchItem);

        void onItemPredictClick(View view, int position, Result matchItem);

        void onItemLogoTeamClick(View view, Integer id, String logo, String name);
    }
}


