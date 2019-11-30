package com.traap.traapapp.ui.adapters.photo;

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

import java.util.ArrayList;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.mainVideos.Category;

/**
 * Created by MahtabAzizi on 11/27/2019.
 */
public class PhotosArchiveAdapter extends RecyclerView.Adapter<PhotosArchiveAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Category> recent;
    private ArchiveVideoListener listener;

    public PhotosArchiveAdapter(ArrayList<Category> recent, ArchiveVideoListener listener)
    {
        this.recent=recent;
        this.listener=listener;
    }


    @Override
    public PhotosArchiveAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new PhotosArchiveAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_archive_photo, parent, false));
    }


    @Override
    public void onBindViewHolder(final PhotosArchiveAdapter.ViewHolder holder, final int position)
    {
        Category recentItem = recent.get(position);
        holder.tvTitleVideo.setText(recentItem.getTitle());
        setImageBackground(holder.ivArchiveVideo,recentItem.getCover().replace("\\", ""));
        holder.ivArchiveVideo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemArchiveVideoClick(position,recentItem,recent);
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
                public void onError()
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
        public RoundedImageView ivArchiveVideo;
        public TextView tvTitleVideo;

        public ViewHolder(View v)
        {
            super(v);
            tvTitleVideo=v.findViewById(R.id.tvTitleVideo);
            ivArchiveVideo=v.findViewById(R.id.ivArchiveVideo);

        }
    }

    public interface ArchiveVideoListener {
        void onItemArchiveVideoClick(int position, Category category, ArrayList<Category> recent);
    }

}
