package com.traap.traapapp.ui.adapters.billTollAndTraficPlan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.billCar.Detail;
import com.traap.traapapp.utilities.Utility;

import java.util.List;

public class AllBillTollAdapter extends RecyclerView.Adapter<AllBillTollAdapter.ViewHolder>
{
    private AllBillTollAdapter.OnItemAllBillClickListener mItemClickListener;
    private Context mContext;
    private List<Detail> list;
    private int row_index = 0;
    private View view;

    public int getRow_index()
    {
        return row_index;
    }

    public void setRow_index(int row_index)
    {
        this.row_index = row_index;
    }

    public AllBillTollAdapter(Context mContext, List<Detail> list, OnItemAllBillClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
        this.mContext = mContext;
        this.list = list;
    }
    public AllBillTollAdapter(Context mContext, List<Detail> list)
    {
        this.mItemClickListener = mItemClickListener;
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_bill_toll, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Detail item = list.get(position);


        holder.txtPrice.setText(Utility.priceFormat(String.valueOf(item.getAmount())) + " ریال");



        holder.cbDate.setTag(item.getBillId());
        holder.cbDate.setChecked(item.getCheck());
        holder.cbDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                list.get(position).setCheck(isChecked);

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtPrice;
        public CheckBox cbDate;
        public ProgressBar progressBar;
        public LinearLayout rootView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
            rootView = itemView.findViewById(R.id.rootView);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            cbDate = itemView.findViewById(R.id.cbDate);
          //  checkBox.setClickable(false);
           // image = itemView.findViewById(R.id.image);
          //  progressBar = itemView.findViewById(R.id.progress);


        }


    }


    public interface OnItemAllBillClickListener
    {
        public void OnItemAllBillClick(View view, String id, String amount);
    }



}
