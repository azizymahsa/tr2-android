package com.traap.traapapp.ui.adapters.news;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.news.main.ImageName;

public class NewsDetailsImageAdapter extends RecyclerView.Adapter<NewsDetailsImageAdapter.ViewHolder>
{
    private Context mContext;
    private List<ImageName> list;

    public NewsDetailsImageAdapter(Context mContext, List<ImageName> list)
    {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_news_content_image_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        ImageName news = list.get(position);

        if (news.getThumbnailLarge() != null)
        {
            setImageBackground(holder.progress, holder.imgBackground, news.getThumbnailLarge());
        }

    }


    private void setImageBackground(ProgressBar progressBar, ImageView imageView, String link)
    {
        try
        {
            Picasso.with(mContext).load(Uri.parse(link)).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
        catch (NullPointerException e)
        {
            Picasso.with(mContext).load(R.drawable.img_failure).into(imageView);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
//            implements  View.OnClickListener
    {
        private ImageView imgBackground;
        private ProgressBar progress;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            imgBackground = rootView.findViewById(R.id.image);

            progress = rootView.findViewById(R.id.progress);

//            rootView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view)
//        {
//            if (mItemClickListener != null)
//            {
//                mItemClickListener.onSliderItemClick(view,  list.get(getAdapterPosition()).getId(), getAdapterPosition());
//            }
//        }
    }


//    public interface OnSliderItemClickListener
//    {
//        public void onSliderItemClick(View view, Integer id, Integer position);
//    }
//
//    public void SetOnItemClickListener(final OnSliderItemClickListener mItemClickListener)
//    {
//        this.mItemClickListener = mItemClickListener;
//    }

}
