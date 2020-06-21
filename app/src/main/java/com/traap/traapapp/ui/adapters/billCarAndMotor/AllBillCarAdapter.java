package com.traap.traapapp.ui.adapters.billCarAndMotor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.allService.response.SubMenu;
import com.traap.traapapp.apiServices.model.billCar.Detail;
import com.traap.traapapp.models.otherModels.mainService.MainServiceModelItem;
import com.traap.traapapp.utilities.Utility;

import java.util.List;

public class AllBillCarAdapter extends RecyclerView.Adapter<AllBillCarAdapter.ViewHolder>
{
    private AllBillCarAdapter.OnItemAllBillClickListener mItemClickListener;
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

    public AllBillCarAdapter(Context mContext, List<Detail> list, OnItemAllBillClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
        this.mContext = mContext;
        this.list = list;
    }
    public AllBillCarAdapter(Context mContext, List<Detail> list)
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
                R.layout.row_bill_car, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Detail item = list.get(position);
       // public TextView txtDate,txtCity,txtLocat,txtPrice,txtType;

        holder.txtDate.setText(item.getDate());
        holder.txtCity.setText(item.getCity());
        holder.txtLocat.setText(item.getLocation());
        holder.txtPrice.setText(Utility.priceFormat(String.valueOf(item.getAmount())) + " ریال");
        holder.txtType.setText(item.getType());
        holder.rootView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                row_index = position;
                mItemClickListener.OnItemAllBillClick(view, item.getBillId(), item.getAmount()+"");
                notifyDataSetChanged();

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
        public TextView txtDate,txtCity,txtLocat,txtPrice,txtType;
        public ImageView image;
        public ProgressBar progressBar;
        public LinearLayout rootView;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            view = itemView;
            rootView = itemView.findViewById(R.id.rootView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtLocat = itemView.findViewById(R.id.txtLocat);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtType = itemView.findViewById(R.id.txtType);
           // image = itemView.findViewById(R.id.image);
          //  progressBar = itemView.findViewById(R.id.progress);


        }


    }


    public interface OnItemAllBillClickListener
    {
        public void OnItemAllBillClick(View view, String id,String amount);
    }



}
