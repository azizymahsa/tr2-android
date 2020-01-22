package com.traap.traapapp.ui.adapters.points;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.points.groupBy.PointGroupBy;
import com.traap.traapapp.apiServices.model.points.records.PointRecord;
import com.traap.traapapp.utilities.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PointGroupByParentAdapter extends RecyclerView.Adapter<PointGroupByParentAdapter.ViewHolder>
{
    private Context mContext;
    private List<PointGroupBy> list;
    private List<Integer> heightList;

    public PointGroupByParentAdapter(Context mContext, List<PointGroupBy> list)
    {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_point_records, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        PointGroupBy item = list.get(position);

//        if (item.getDate() != null)
//        {
//            holder.tvDate.setText(item.getDate());
//        }
//
//        if (item.getTitle() != null)
//        {
//            holder.tvTitle.setText(item.getTitle());
//        }
//
//        if (Objects.requireNonNull(item.getValue()) != null)
//        {
//            holder.tvValue.setText(item.getValue() + " تراپ");
//        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvTitle, tvDate, tvValue;
        private LinearLayout root;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            root = rootView.findViewById(R.id.rootView);
            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvDate = rootView.findViewById(R.id.tvDate);
            tvValue = rootView.findViewById(R.id.tvValue);
        }
    }

}
