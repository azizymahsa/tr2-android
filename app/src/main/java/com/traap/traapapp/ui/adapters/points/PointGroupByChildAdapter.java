package com.traap.traapapp.ui.adapters.points;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.points.records.PointRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PointGroupByChildAdapter extends RecyclerView.Adapter<PointGroupByChildAdapter.ViewHolder>
{
    private Context mContext;
    private List<PointRecord> list;
    private List<Integer> heightList;

    public PointGroupByChildAdapter(Context mContext, List<PointRecord> list, List<Integer> heightList)
    {
        this.mContext = mContext;
        this.list = list;
        this.heightList = heightList;
    }

    public PointGroupByChildAdapter(Context mContext, List<PointRecord> list)
    {
        this.mContext = mContext;
        this.list = list;
        this.heightList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_point_group_by_child, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        PointRecord item = list.get(position);

        if (item.getDate() != null)
        {
            holder.tvDate.setText(item.getDate());
        }

        if (item.getTitle() != null)
        {
            holder.tvTitle.setText(item.getTitle());
        }

        if (Objects.requireNonNull(item.getValue()) != null)
        {
            holder.tvValue.setText(item.getValue() + " تراپ");
        }

//        holder.root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
//        {
//            @Override
//            public void onGlobalLayout()
//            {
//                holder.root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                heightList.add(holder.root.getMeasuredHeight());
////                heightItemView.getHeight(holder.root.getMeasuredHeight());
//                Logger.e("-heightItemView-", String.valueOf(holder.root.getMeasuredHeight()));
//            }
//        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public int getMaxHeightViewItem()
    {
        return Collections.max(heightList);
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
