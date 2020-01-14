package com.traap.traapapp.ui.fragments.turnover;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getReport.response.ListTransaction;
import com.traap.traapapp.apiServices.model.getTransaction.TransactionList;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.activities.paymentResult.PaymentResultActivity;
import com.traap.traapapp.ui.activities.paymentResult.PaymentResultChargeActivity;
import com.traap.traapapp.ui.activities.ticket.ShowTicketActivity;
import com.traap.traapapp.utilities.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

import static androidx.core.content.ContextCompat.getColor;


public class TurnoverAdapter extends RecyclerView.Adapter<TurnoverAdapter.ViewHolder>
{
    private final Context mContext;
    private LayoutInflater mInflater;
    private List<ListTransaction> listTransaction;



    public TurnoverAdapter(Context mContext, List<ListTransaction> listTransaction)
    {
        this.mContext = mContext;
        this.listTransaction = listTransaction;
        this.mInflater = LayoutInflater.from(mContext);
        //credid afzayesh
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.row_turn_over, parent, false);
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

        if (listTransaction.get(position).getCreditAmount()==0){
            holder.txtPrice.setText("قیمت: " + Utility.priceFormat(listTransaction.get(position).getDebitAmount() + "") + " ریال");

        }else{
            holder.txtPrice.setText("قیمت: " + Utility.priceFormat(listTransaction.get(position).getCreditAmount() + "") + " ریال");

        }

        holder.txtDate.setText((listTransaction.get(position).getCreateDate()));
        holder.txtDesc.setText((listTransaction.get(position).getDesc()));



    }

    // total number of rows
    @Override
    public int getItemCount()
    {
        return listTransaction.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView txtStatus;
        TextView txtPrice;
        TextView txtDate;
        TextView txtCheck;
        TextView txtDesc;


        ImageView ivFlagCheck;
        LinearLayout llItem;

        ViewHolder(View itemView)
        {
            super(itemView);

            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtCheck = itemView.findViewById(R.id.txtCheck);
            ivFlagCheck = itemView.findViewById(R.id.ivFlagCheck);


            llItem = itemView.findViewById(R.id.llItem);
        }

    }


    public interface ItemClickListener
    {
        void onItemClick(View view, int position);
    }
}

