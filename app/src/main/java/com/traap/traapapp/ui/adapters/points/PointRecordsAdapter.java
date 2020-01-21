package com.traap.traapapp.ui.adapters.points;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.news.main.News;
import com.traap.traapapp.apiServices.model.points.records.PointRecord;
import com.traap.traapapp.utilities.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PointRecordsAdapter extends RecyclerView.Adapter<PointRecordsAdapter.ViewHolder>
{
    private Context mContext;
    private List<PointRecord> list;
    private List<Integer> heightList;

    public PointRecordsAdapter(Context mContext, List<PointRecord> list, List<Integer> heightList)
    {
        this.mContext = mContext;
        this.list = list;
        this.heightList = heightList;
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

        holder.root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                holder.root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                heightList.add(holder.root.getMeasuredHeight());
//                heightItemView.getHeight(holder.root.getMeasuredHeight());
                Logger.e("-heightItemView-", String.valueOf(holder.root.getMeasuredHeight()));
            }
        });

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
