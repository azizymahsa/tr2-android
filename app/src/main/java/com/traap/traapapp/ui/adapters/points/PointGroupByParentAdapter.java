package com.traap.traapapp.ui.adapters.points;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
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
                R.layout.adapter_point_group_by, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        PointGroupBy item = list.get(position);

        if (item.getTitle() != null)
        {
            holder.tvTitle.setText(item.getTitle());
        }
//
        if (Objects.requireNonNull(item.getCount()) != null)
        {
            holder.tvCount.setText(item.getCount() + " بار");
        }

        if (Objects.requireNonNull(item.getValueSum()) != null)
        {
            holder.tvValueSum.setText(item.getValueSum() + " تراپ");
        }

        if (!item.getPointChildList().isEmpty())
        {
            holder.rcChild.setLayoutManager(new GridLayoutManager(mContext, 1));
            holder.rcChild.setAdapter(new PointGroupByChildAdapter(mContext, item.getPointChildList()));
        }

        holder.llParent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!item.getPointChildList().isEmpty())
                {
                    if (holder.rcChild.getVisibility() == View.VISIBLE)
                    {
                        holder.rcChild.setVisibility(View.GONE);
                        holder.imgIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_plus));
                    }
                    else
                    {
                        holder.rcChild.setVisibility(View.VISIBLE);
                        holder.imgIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_minus));
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
//            implements View.OnClickListener
    {
        private TextView tvTitle, tvCount, tvValueSum;
        private ImageView imgIcon;
        private RecyclerView rcChild;
        private LinearLayout root, llParent;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            root = rootView.findViewById(R.id.rootView);
            llParent = rootView.findViewById(R.id.llParent);

            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvCount = rootView.findViewById(R.id.tvCount);
            tvValueSum = rootView.findViewById(R.id.tvValueSum);

            imgIcon = rootView.findViewById(R.id.imgIcon);

            rcChild = rootView.findViewById(R.id.rcChild);

//            llParent.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v)
//        {
//            if (rcChild.getVisibility() == View.VISIBLE)
//            {
//                rcChild.setVisibility(View.GONE);
//                imgIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_minus));
//            }
//            else
//            {
//                rcChild.setVisibility(View.VISIBLE);
//                imgIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_plus));
//            }
//        }
    }

}
