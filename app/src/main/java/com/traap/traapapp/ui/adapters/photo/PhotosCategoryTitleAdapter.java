package com.traap.traapapp.ui.adapters.photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.mainVideos.ListCategory;
import com.traap.traapapp.ui.activities.photo.PhotoArchiveActivity;
import com.traap.traapapp.ui.fragments.main.MainActionView;

/**
 * Created by MahsaAzizi on 11/25/2019.
 */
public class PhotosCategoryTitleAdapter extends RecyclerView.Adapter<PhotosCategoryTitleAdapter.ViewHolder>
{
    private  boolean flag=true;
    private  MainActionView mainView;
    private Context context;
    private List<ListCategory> categories;
    private TitleCategoryListener listener;
    private boolean isClicked=false;
    private int selectedPosition=0;

    public PhotosCategoryTitleAdapter(List<ListCategory> categories, MainActionView mainView,TitleCategoryListener listener)
    {
        this.listener=listener;
        this.categories=categories;
        this.mainView=mainView;
    }
    public PhotosCategoryTitleAdapter(List<ListCategory> categories,TitleCategoryListener listener)
    {
        this.listener=listener;
        this.categories=categories;
    }



    public PhotosCategoryTitleAdapter(ArrayList<ListCategory> categories, PhotoArchiveActivity mainView, boolean flag)
    {
      //  this.listener=listener;
        this.categories=categories;
        this.flag=flag;
    }




    @Override
    public PhotosCategoryTitleAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new PhotosCategoryTitleAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_category_title_video, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        ListCategory category = categories.get(position);
        holder.tvTitle.setText(category.getTitle());
        //    holder.tvTitle.setTextColor(context.getResources().getColor(R.color.black));

        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition=position;
                notifyDataSetChanged();
                //if(flag)
                listener.onItemTitleCategoryClick(category);
            }
        });
        if(selectedPosition==position){
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.borderColorRed));
            holder.tvTitle.setBackgroundResource(R.drawable.background_border_a);

        }
        else
        {
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvTitle.setBackgroundResource(0);

        }
    }


    @Override
    public int getItemCount()
    {

        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvTitle;

        public ViewHolder(View v)
        {
            super(v);
            tvTitle=v.findViewById(R.id.tvTitle);

        }
    }
    public interface TitleCategoryListener {
        void onItemTitleCategoryClick(ListCategory category);
    }
}
