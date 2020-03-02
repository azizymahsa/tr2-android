package com.traap.traapapp.ui.adapters.lotteryWinnerList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.apiServices.model.points.records.PointRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LotteryPredictDetailsAdapter extends RecyclerView.Adapter<LotteryPredictDetailsAdapter.ViewHolder>
{
    private Context mContext;
    private List<Winner> list;

    public LotteryPredictDetailsAdapter(Context mContext, List<Winner> list)
    {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_predict_lottery_details, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Winner item = list.get(position);

        holder.tvFullName.setText(item.getFirstName() + " " + item.getLastName());

        holder.tvMobile.setText(item.getMobile());

        holder.tvLotteryTitle.setText(item.getDescription());

        holder.tvIndex.setText(String.valueOf(position + 1));

        if (position == list.size()-1)
        {
            holder.viewBottom.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvLotteryTitle, tvMobile, tvFullName, tvIndex;
        private View  viewBottom;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            viewBottom = rootView.findViewById(R.id.viewBottom);

            tvLotteryTitle = rootView.findViewById(R.id.tvLotteryTitle);
            tvMobile = rootView.findViewById(R.id.tvMobile);
            tvFullName = rootView.findViewById(R.id.tvFullName);
            tvIndex = rootView.findViewById(R.id.tvIndex);
        }
    }

}
