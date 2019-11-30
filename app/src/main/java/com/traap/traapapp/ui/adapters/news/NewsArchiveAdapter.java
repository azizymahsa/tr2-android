package com.traap.traapapp.ui.adapters.news;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.news.main.News;
import com.traap.traapapp.utilities.Logger;

public class NewsArchiveAdapter extends RecyclerView.Adapter<NewsArchiveAdapter.ViewHolder>
{
    private OnSliderItemClickListener mItemClickListener;
    private Context mContext;
    private List<News> list;

    public NewsArchiveAdapter(Context mContext, List<News> list)
    {
        this.mContext = mContext;
        this.list = list;
        Logger.e("+++ adapter size +++", "size=" + list.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_news_archive, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        News item = list.get(position);

        if (item.getCreateDate() != null)
        {
            holder.tvDate.setText(item.getCreateDate());
        }

        if (item.getTitle() != null)
        {
            holder.tvTitle.setText(item.getTitle());
        }

        if (item.getImageName() != null)
        {
            setImageBackground(holder.progress, holder.imageView, item.getImageName().getThumbnailSmall());
        }
    }


    private void setImageBackground(ProgressBar progress, ImageView imageView, String link)
    {
        try
        {
            Picasso.with(mContext).load(Uri.parse(link)).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
                    progress.setVisibility(View.GONE);
                }
            });
        }
        catch (NullPointerException e)
        {
            Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        private TextView tvTitle, tvDate;
        private ImageView imageView;
        private ProgressBar progress;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            imageView = rootView.findViewById(R.id.image);
            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvDate = rootView.findViewById(R.id.tvDate);
            progress = rootView.findViewById(R.id.progress);

            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mItemClickListener != null)
            {
                mItemClickListener.onSliderItemClick(list.get(getAdapterPosition()).getId(), list.get(getAdapterPosition()),  getAdapterPosition());
            }
        }
    }


    public interface OnSliderItemClickListener
    {
        public void onSliderItemClick(Integer id, News newsArchiveContent, Integer position);
    }

    public void SetOnItemClickListener(final OnSliderItemClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
    }

}
