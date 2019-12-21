package com.traap.traapapp.ui.adapters.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.news.category.response.NewsArchiveCategory;
import com.traap.traapapp.apiServices.model.news.main.News;

import java.util.List;

public class NewsArchiveFilterAdapter extends RecyclerView.Adapter<NewsArchiveFilterAdapter.ViewHolder>
{
    private OnItemCheckedChangeListener mItemClickListener;
    private Context mContext;
    private List<NewsArchiveCategory> list;

    public NewsArchiveFilterAdapter(Context mContext, List<NewsArchiveCategory> list)
    {
        this.mContext = mContext;
        this.list = list;
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
        NewsArchiveCategory item = list.get(position);

        if (item.getTitle() != null)
        {
            holder.tvTitle.setText(item.getTitle());
        }

        holder.checkbox.setChecked(false);
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
                mItemClickListener.onItemCheckedChange(list.get(getAdapterPosition()).getId(), getAdapterPosition());
            }
        }
    }


    public interface OnItemCheckedChangeListener
    {
        public void onItemCheckedChange(Integer id, Integer position);
    }

    public void SetOnItemCheckedChangeListener(final OnItemCheckedChangeListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
    }

}
