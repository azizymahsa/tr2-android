package com.traap.traapapp.ui.adapters.transaction;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getTransaction.Result;
import com.traap.traapapp.ui.activities.ticket.ShowTicketActivity;
import library.android.eniac.utility.Utility;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

import static androidx.core.content.ContextCompat.getColor;


public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder>
{
    private final Context mContext;
    private final List<Result> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private String refrenceNumber;
    private Integer typeTransaction = 0;


    public TransactionListAdapter(List<Result> mData, Context mContext)
    {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.row_transaction_result, parent, false);
        return new ViewHolder(view);
    }

    private String getDate(Date d)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(d);  // formatted date in string
        String[] splitsDate = date.split("-");

        PersianDate persianDate = new PersianDate();
        persianDate.setGrgYear(Integer.valueOf(splitsDate[0]));
        persianDate.setGrgMonth(Integer.valueOf(splitsDate[1]));
        persianDate.setGrgDay(Integer.valueOf(splitsDate[2]));

        PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d H:i");
        pdformater1.format(persianDate);//1396/05/20 15:21

        date = String.valueOf(pdformater1.format(persianDate));//1396/05/20

        return date;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

        Result item = mData.get(position);


        if (item.getStatus())
        {
            holder.txtStatus.setText(mContext.getString(R.string.success));
            holder.txtCheck.setTextColor(getColor(mContext, R.color.green));
            holder.ivFlagCheck.setImageResource(R.drawable.check_mark);

        } else
        {
            holder.txtCheck.setTextColor(getColor(mContext, R.color.redBack));
            holder.txtStatus.setText(mContext.getString(R.string.un_success));
            holder.ivFlagCheck.setImageResource(R.drawable.un_check_mark);
        }
        holder.txtPrice.setText("قیمت: " + Utility.priceFormat(item.getAmount() + "") + " ریال");


        holder.llItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (item.getStatus())
                {
                    if (item.getTypeTransactionId() == 12)
                    {
                        Intent intent = new Intent(mContext, ShowTicketActivity.class);

                        intent.putExtra("RefrenceNumber", item.getId().toString());
                        intent.putExtra("isTransactionList", true);
                        mContext.startActivity(intent);
                    }
                }
            }
        });


        holder.txtDate.setText(item.getCreate_date_formatted());


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

        TextView txtStatus;
        TextView txtPrice;
        TextView txtDate;
        TextView txtCheck;


        ImageView ivFlagCheck;
        ImageView ivDec;
        LinearLayout llItem;

        ViewHolder(View itemView)
        {
            super(itemView);

            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtCheck = itemView.findViewById(R.id.txtCheck);
            ivFlagCheck = itemView.findViewById(R.id.ivFlagCheck);
            ivDec = itemView.findViewById(R.id.ivDec);


            llItem = itemView.findViewById(R.id.llItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.llItem:
                    if (typeTransaction == 12)
                    {
                        Intent intent = new Intent(mContext, ShowTicketActivity.class);

                        intent.putExtra("RefrenceNumber", refrenceNumber);
                        mContext.startActivity(intent);
                    }
                    break;
            }
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener
    {
        void onItemClick(View view, int position);
    }
}

