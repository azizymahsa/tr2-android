package com.traap.traapapp.ui.adapters.video;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.ui.fragments.main.MainActionView;

/**
 * Created by MahtabAzizi on 11/25/2019.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Category> categories;
    private CategoryListener listener;

    public CategoryAdapter(ArrayList<Category> categories,CategoryListener listener)
    {
        this.categories=categories;
        this.listener=listener;
    }


    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new CategoryAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_category_video, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        Category category = categories.get(position);

        setImageBackground(holder.ivCategory,category.getBigPoster().replace("\\", ""));
        holder.ivCategory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemCategoryClick(position,category,categories);
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

        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView ivCategory;

        public ViewHolder(View v)
        {
            super(v);
            ivCategory=v.findViewById(R.id.ivCategory);

        }
    }
    public interface CategoryListener {
        void onItemCategoryClick(int position, Category category, ArrayList<Category> categories);
    }
}
