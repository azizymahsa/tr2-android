package com.traap.traapapp.ui.adapters.photo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.ui.fragments.main.MainActionView;

/**
 * Created by MahsaAzizi on 11/27/2019.
 */
public class CategoryPhotosAdapter extends RecyclerView.Adapter<CategoryPhotosAdapter.ViewHolder>
{
    private Context context;
    private List<Category> categories;
    private TitleCategoryListener listener;

    public CategoryPhotosAdapter(List<Category> categories,TitleCategoryListener listener)
    {
        this.categories=categories;
        this.listener=listener;
    }


    @Override
    public CategoryPhotosAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new CategoryPhotosAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_category_photo, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        Category category = categories.get(position);

        setImageBackground(holder.ivCategory,category.getCover().replace("\\", ""));
        holder.ivCategory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onItemTitleCategoryClick(category);
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
        public com.makeramen.roundedimageview.RoundedImageView ivCategory;

        public ViewHolder(View v)
        {
            super(v);
            ivCategory=v.findViewById(R.id.ivCategory);

        }
    }
    public interface TitleCategoryListener {
        void onItemTitleCategoryClick(Category category);
    }
}
