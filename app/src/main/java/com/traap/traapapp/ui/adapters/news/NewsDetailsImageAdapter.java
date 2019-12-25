package com.traap.traapapp.ui.adapters.news;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.news.main.ImageName;
import com.traap.traapapp.apiServices.model.photo.response.Content;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.fragments.news.details.contentNews.NewsDetailsContentFragment;

public class NewsDetailsImageAdapter extends RecyclerView.Adapter<NewsDetailsImageAdapter.ViewHolder>
{
    private final NewsDetailsContentFragment mItemClickListener;
    private Context mContext;
    private List<ImageName> list;

    public NewsDetailsImageAdapter(Context mContext, List<ImageName> list, NewsDetailsContentFragment mItemClickListener)
    {
        this.mContext = mContext;
        this.list = list;
        this.mItemClickListener = mItemClickListener;
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

        int width = (int) (SingletonContext.getInstance().getWidth() - mContext.getResources().getDimension(R.dimen.margin_news_main_favorite));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, R.dimen.news_favorite_height);
        holder.rlImage.setLayoutParams(params);

        if (news.getThumbnailLarge() != null)
        {
            setImageBackground(holder.progress, holder.imgBackground, news.getThumbnailLarge());
        }
        holder.rlImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
              //  row_index = position;
                mItemClickListener.OnItemNewsClick(view, news);
                notifyDataSetChanged();

            }
        });
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
    public interface OnItemNewsClickListener
    {
        public void OnItemNewsClick(View view, ImageName content);
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imgBackground;
        private ProgressBar progress;
        private RelativeLayout rlImage;

        public ViewHolder(@NonNull View rootView)
        {
            super(rootView);

            imgBackground = rootView.findViewById(R.id.image);
            rlImage = rootView.findViewById(R.id.rlImage);

            progress = rootView.findViewById(R.id.progress);
        }

    }
}
