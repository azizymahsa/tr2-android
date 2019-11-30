package com.traap.traapapp.ui.adapters.Leaguse.pastResult;

import android.content.Context;
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
import com.traap.traapapp.apiServices.model.league.pastResult.response.Result;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class PastResultAdapter extends RecyclerView.Adapter<PastResultAdapter.ViewHolder>
{
    private final Context mContext;
    private final List<Result> mData;
    // private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    public PastResultAdapter(List<Result> mData, Context mContext)
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

    private String getDate(Date d)
    {



        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd" );
        String date = dateFormat.format(d);  // formatted date in string
        String[] splitsDate = date.split("-");

        PersianDate persianDate = new PersianDate();
        persianDate.setGrgYear(Integer.valueOf(splitsDate[0]));
        persianDate.setGrgMonth(Integer.valueOf(splitsDate[1]));
        persianDate.setGrgDay(Integer.valueOf(splitsDate[2]));


        PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");
        pdformater1.format(persianDate);//1396/05/20

        //PersianDateFormat pdformater2 = new PersianDateFormat("l j F y ");
        // date = String.valueOf(pdformater2.format(pdate));//۱۹ تیر ۹۶
        date = String.valueOf(pdformater1.format(persianDate));//1396/05/20

        return date;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

        Result item = mData.get(position);

        holder.tvNameAway.setText(item.getAwayName());
        holder.tvNameHome.setText(item.getHomeName());
        holder.txtCompetition_name.setText(item.getCompetitionName());


       /* String strDate = item.getDate();//"2013-05-15T10:00:00-0700";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = null;
        try
        {
            date = dateFormat.parse(strDate);
            Log.d("time:",getDate(date)+" $$$$$$$"+item.getDate());*/
            holder.tvDateLeage.setText(item.getDateFormatted());
        /*} catch (ParseException e)
        {
            e.printStackTrace();
        }*/
        //-----------------------------------------------------------------------------------------------
        try
        {
            String s = item.getFtScore();//2-0
            String[] i = s.split("-");
            holder.ftScoreAway.setText(i[1]);//2
            holder.ftScoreHome.setText(i[0]);//0
        } catch (Exception e)
        {
            e.getMessage();
        }
        //-----------------------------------------------------------------------------------------------
        try
        {
            Picasso.with(mContext).load(item.getHomeLogo()).into(holder.ivNameHome, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    // holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    //holder.progressBar.setVisibility(View.GONE);
                    Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.ivNameHome);
                }
            });
        } catch (Exception e1)
        {
            //  holder.progressBar.setVisibility(View.GONE);
            Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.ivNameAway);
        }
        //-----------------------------------------------------------------------------------------------
        try
        {
            Picasso.with(mContext).load(item.getAwayLogo()).into(holder.ivNameAway, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    // holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    //holder.progressBar.setVisibility(View.GONE);
                    Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.ivNameAway);
                }
            });
        } catch (Exception e1)
        {
            //  holder.progressBar.setVisibility(View.GONE);
            Picasso.with(mContext).load(R.drawable.ic_logo_red).into(holder.ivNameAway);
        }
        //-----------------------------------------------------------------------------------------------


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

