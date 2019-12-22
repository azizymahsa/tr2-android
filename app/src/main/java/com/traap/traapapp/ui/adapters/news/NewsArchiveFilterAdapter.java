package com.traap.traapapp.ui.adapters.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.models.otherModels.newsFilterItem.FilterItem;

import java.util.ArrayList;
import java.util.List;

public class NewsArchiveFilterAdapter extends RecyclerView.Adapter<NewsArchiveFilterAdapter.ViewHolder>
{
    private OnItemCheckedChangeListener mItemClickListener;
    private Context mContext;
    private List<FilterItem> list;

    public NewsArchiveFilterAdapter(Context mContext, List<FilterItem> list)
    {
        this.mContext = mContext;
        this.list = new ArrayList<>(list.size());
        this.list.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_news_archive_filter, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        FilterItem item = list.get(position);

        if (item.getTitle() != null)
        {
            holder.tvTitle.setText(item.getTitle());
        }

        holder.checkbox.setChecked(item.isChecked());
    }


    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  CompoundButton.OnCheckedChangeListener
    {
//        private RelativeLayout root;
        private TextView tvTitle;
        private CheckBox checkbox;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

//            root = rootView.findViewById(R.id.root);
            tvTitle = rootView.findViewById(R.id.tvTitle);
            checkbox = rootView.findViewById(R.id.checkbox);

            checkbox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            if (mItemClickListener != null)
            {
                list.get(getAdapterPosition()).setChecked(isChecked);

                mItemClickListener.onItemCheckedChange(
                        list.get(getAdapterPosition()).getId(),
                        isChecked,
                        list.get(getAdapterPosition())
                );
            }
        }
    }


    public interface OnItemCheckedChangeListener
    {
        public void onItemCheckedChange(Integer id, boolean isChecked, FilterItem filterItem);
    }

    public void SetOnItemCheckedChangeListener(final OnItemCheckedChangeListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
    }

}
