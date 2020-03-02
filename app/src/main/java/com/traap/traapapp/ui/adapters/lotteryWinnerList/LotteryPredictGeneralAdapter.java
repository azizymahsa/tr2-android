package com.traap.traapapp.ui.adapters.lotteryWinnerList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.apiServices.model.points.records.PointRecord;

import java.util.List;

public class LotteryPredictGeneralAdapter extends RecyclerView.Adapter<LotteryPredictGeneralAdapter.ViewHolder>
{
    private Context mContext;
    private List<Winner> list;
    private Boolean isFinal;

    public LotteryPredictGeneralAdapter(Context mContext, List<Winner> list, Boolean isFinal)
    {
        this.mContext = mContext;
        this.list = list;
        this.isFinal = isFinal;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_predict_lottery_general, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Winner item = list.get(position);
        int index = position + 1;

        if (!isFinal)
        {
            holder.tvTitle.setText("نفر " + index + " :" + item.getDescription());
        }
        else
        {
            holder.tvTitle.setText("نفر " + index + " :" + item.getFirstName() + " " + item.getLastName() + " (" + item.getDescription() + " )");
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvTitle;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            tvTitle = rootView.findViewById(R.id.tvTitle);
        }
    }

}
