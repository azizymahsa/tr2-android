package com.traap.traapapp.ui.adapters.video;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import java.util.List;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.ui.fragments.main.MainActionView;

/**
 * Created by MahtabAzizi on 11/23/2019.
 */
public class NewestVideosAdapter extends RecyclerView.Adapter<NewestVideosAdapter.ViewHolder>
{
    private Context context;
    private List<Category> recent;
    private NewestVideoListener listener;





    public NewestVideosAdapter(List<Category> recent,NewestVideoListener listener)
    {
        this.recent=recent;
        this.listener=listener;
    }


    @Override
    public NewestVideosAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new NewestVideosAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_newest_video, parent, false));
    }


    @Override
    public void onBindViewHolder(final NewestVideosAdapter.ViewHolder holder, final int position)
    {
        Category recentItem = recent.get(position);
        holder.tvTitleVideo.setText(recentItem.getTitle());
        holder.tvLike.setText(recentItem.getLikes().toString());
        setImageBackground(holder.ivNewestVideo,recentItem.getBigPoster().replace("\\", ""));
        holder.ivNewestVideo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemNewestVideoClick(position,recentItem);
            }
        });

    }

    private void setImageBackground(ImageView image, String link)
    {
        try
        {
            Glide.with(context).load(Uri.parse(link)).into(image);
       /*     Picasso.with(context).load(Uri.parse(link)).into(image, new Callback()
            {
                @Override
                public void onSuccess() { }

                @Override
                public void showErrorMessage()
                {
                    Picasso.with(context).load(R.drawable.img_failure).into(image);
                }
            });*/
        }
        catch (NullPointerException e)
        {
            Picasso.with(context).load(R.drawable.img_failure).into(image);
        }
    }



    @Override
    public int getItemCount()
    {

        return recent.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public RoundedImageView ivNewestVideo;
        public TextView tvTitleVideo,tvLike;

        public ViewHolder(View v)
        {
            super(v);
            tvTitleVideo=v.findViewById(R.id.tvTitleVideo);
            ivNewestVideo=v.findViewById(R.id.ivNewestVideo);
            tvLike=v.findViewById(R.id.tvLike);

        }
    }

    public interface NewestVideoListener {
        void onItemNewestVideoClick(int position, Category category);
    }

}
