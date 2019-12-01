package com.traap.traapapp.ui.adapters.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.mainVideos.ListCategory;
import com.traap.traapapp.ui.fragments.main.MainActionView;

/**
 * Created by MahtabAzizi on 11/25/2019.
 */
public class VideosCategoryTitleAdapter extends RecyclerView.Adapter<VideosCategoryTitleAdapter.ViewHolder>
{
    private MainActionView mainView;
    private Context context;
    private List<ListCategory> categories;
    private TitleCategoryListener listener;
    private boolean isClicked = false;
    private int selectedPosition = 0;

    public VideosCategoryTitleAdapter(List<ListCategory> categories, MainActionView mainView, TitleCategoryListener listener)
    {
        this.listener = listener;
        this.categories = categories;
        this.mainView = mainView;
    }

    public VideosCategoryTitleAdapter(List<ListCategory> categories, TitleCategoryListener listener)
    {
        this.listener = listener;
        this.categories = categories;
    }


    @Override
    public VideosCategoryTitleAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new VideosCategoryTitleAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_category_title_video, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        ListCategory category = categories.get(position);
        holder.tvTitle.setText(category.getTitle());
        //    holder.tvTitle.setTextColor(context.getResources().getColor(R.color.black));

        holder.tvTitle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectedPosition = position;
                notifyDataSetChanged();
                listener.onItemTitleCategoryClick(category, position);
            }
        });
        if (selectedPosition == position)
        {
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.borderColorRed));
            holder.tvTitle.setBackgroundResource(R.drawable.background_border_a);

        } else
        {
            holder.tvTitle.setTextColor(context.getResources().getColor(R.color.black));
            holder.tvTitle.setBackgroundResource(0);

        }
    }


    @Override
    public int getItemCount()
    {
        if (categories == null)
            return 0;
        else
            return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvTitle;

        public ViewHolder(View v)
        {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);

        }
    }

    public interface TitleCategoryListener
    {
        void onItemTitleCategoryClick(ListCategory category, int position);
    }
}
